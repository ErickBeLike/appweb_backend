package com.api.appweb.service;

import com.api.appweb.dto.ProductoCantidadDTO;
import com.api.appweb.dto.VentaDTO;
import com.api.appweb.entity.*;
import com.api.appweb.exception.ResourceNotFoundException;
import com.api.appweb.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private DetalleVentaRepository detalleVentaRepository;

    public List<Venta> obtenerTodasLasVentas() {
        return ventaRepository.findAll();
    }

    public Venta buscarVentaId(Long idVenta) throws ResourceNotFoundException {
        return ventaRepository.findById(idVenta)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró una venta para el ID: " + idVenta));
    }

    public Venta agregarVenta(VentaDTO ventaDTO) throws ResourceNotFoundException {
        Venta venta = new Venta();
        List<DetalleVenta> detallesVenta = new ArrayList<>();
        BigDecimal totalVenta = BigDecimal.ZERO;
        List<String> productosConProblemas = new ArrayList<>(); // Lista para productos con problemas de stock

        for (ProductoCantidadDTO productoCantidadDTO : ventaDTO.getProductos()) {
            Producto producto = productoRepository.findById(productoCantidadDTO.getIdProducto())
                    .orElseThrow(() -> new ResourceNotFoundException("No se encontró un producto para el ID: " + productoCantidadDTO.getIdProducto()));

            int cantidad = productoCantidadDTO.getCantidad();
            int stockActual = producto.getStock(); // Obtener el stock actual del producto

            // Verificar si el stock es 0 o si la cantidad supera al stock
            if (producto.sinStock()) {
                productosConProblemas.add("ID: " + producto.getIdProducto() + " - Nombre: " + producto.getNombreProducto() + " - Stock en 0 (Stock actual: " + stockActual + ")");
                continue; // Saltar al siguiente producto
            }

            if (!producto.tieneSuficienteStock(cantidad)) {
                productosConProblemas.add("ID: " + producto.getIdProducto() + " - Nombre: " + producto.getNombreProducto() + " - Cantidad supera stock (Stock actual: " + stockActual + ")");
                continue; // Saltar al siguiente producto
            }

            // Disminuir el stock del producto y guardar el producto actualizado
            producto.disminuirStock(cantidad);
            productoRepository.save(producto);

            BigDecimal precioUnitario = producto.getPrecioProducto();
            BigDecimal subtotal = precioUnitario.multiply(BigDecimal.valueOf(cantidad)).setScale(2, RoundingMode.HALF_UP);
            totalVenta = totalVenta.add(subtotal);

            DetalleVenta detalleVenta = new DetalleVenta();
            detalleVenta.setVenta(venta);
            detalleVenta.setProducto(producto);
            detalleVenta.setCantidad(cantidad);
            detalleVenta.setPrecioUnitario(precioUnitario);
            detalleVenta.setSubtotal(subtotal);

            detallesVenta.add(detalleVenta);
        }

        // Si hubo productos con problemas de stock, lanzar una excepción con los nombres de los productos
        if (!productosConProblemas.isEmpty()) {
            throw new IllegalArgumentException("Error en venta, los siguientes productos tienen problemas de stock: "
                    + String.join(", ", productosConProblemas));
        }

        // Establecer los detalles de la venta y guardar la venta
        venta.setDetallesVenta(detallesVenta);
        venta.setTotal(totalVenta.setScale(2, RoundingMode.HALF_UP));
        venta.setFechaVenta(LocalDateTime.now());

        return ventaRepository.save(venta);
    }

    @Transactional
    public Venta actualizarVenta(Long idVenta, VentaDTO ventaDTO) throws ResourceNotFoundException {
        Venta venta = ventaRepository.findById(idVenta)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró una venta para el ID: " + idVenta));

        // Eliminar los detalles de venta anteriores
        detalleVentaRepository.deleteAll(venta.getDetallesVenta());

        // Obtener los detalles de venta anteriores
        List<DetalleVenta> detallesVentaAnteriores = venta.getDetallesVenta();

        // Mapa para rastrear los productos y sus cantidades en la venta anterior
        Map<Long, Integer> mapaProductoCantidadAnterior = new HashMap<>();
        for (DetalleVenta detalle : detallesVentaAnteriores) {
            Long idProducto = detalle.getProducto().getIdProducto();
            mapaProductoCantidadAnterior.put(idProducto, detalle.getCantidad());
        }

        // Crear nuevos detalles de venta para los productos proporcionados en la solicitud
        List<DetalleVenta> nuevosDetallesVenta = new ArrayList<>();
        BigDecimal totalVenta = BigDecimal.ZERO;
        List<String> productosConProblemas = new ArrayList<>(); // Lista para productos con problemas de stock

        for (ProductoCantidadDTO productoCantidadDTO : ventaDTO.getProductos()) {
            Producto producto = productoRepository.findById(productoCantidadDTO.getIdProducto())
                    .orElseThrow(() -> new ResourceNotFoundException("No se encontró un producto para el ID: " + productoCantidadDTO.getIdProducto()));

            int cantidadNueva = productoCantidadDTO.getCantidad();
            int cantidadAnterior = mapaProductoCantidadAnterior.getOrDefault(producto.getIdProducto(), 0);
            int stockActual = producto.getStock(); // Obtener el stock actual del producto

            // Revertir la cantidad anterior al stock
            producto.disminuirStock(-cantidadAnterior); // Devolver el stock

            // Verificar si la cantidad nueva supera al stock disponible
            if (producto.sinStock() || !producto.tieneSuficienteStock(cantidadNueva)) {
                productosConProblemas.add("ID: " + producto.getIdProducto() + " - Nombre: " + producto.getNombreProducto() + " - Stock actual: " + stockActual); // Agregar el ID y nombre del producto a la lista de problemas
                producto.disminuirStock(cantidadAnterior); // Revertir la operación anterior
            } else {
                // Actualizar el stock con la nueva cantidad
                producto.disminuirStock(cantidadNueva);
                productoRepository.save(producto);

                // Crear y agregar el detalle de venta
                BigDecimal precioUnitario = producto.getPrecioProducto();
                BigDecimal subtotal = precioUnitario.multiply(BigDecimal.valueOf(cantidadNueva)).setScale(2, RoundingMode.HALF_UP);
                totalVenta = totalVenta.add(subtotal);

                DetalleVenta detalleVenta = new DetalleVenta();
                detalleVenta.setVenta(venta);
                detalleVenta.setProducto(producto);
                detalleVenta.setCantidad(cantidadNueva);
                detalleVenta.setPrecioUnitario(precioUnitario);
                detalleVenta.setSubtotal(subtotal);

                nuevosDetallesVenta.add(detalleVenta);
            }
        }

        // Si hubo productos con problemas de stock, lanzar una excepción con los nombres de los productos
        if (!productosConProblemas.isEmpty()) {
            throw new IllegalArgumentException("Error en venta, cantidad supera a stock disponible para los siguientes productos: "
                    + String.join(", ", productosConProblemas));
        }

        // Actualizar la venta con los nuevos detalles de venta y otros datos actualizados
        venta.setDetallesVenta(nuevosDetallesVenta);
        venta.setTotal(totalVenta.setScale(2, RoundingMode.HALF_UP));
        venta.setFechaVenta(LocalDateTime.now());
        venta.setFechaActualizacion(LocalDateTime.now());

        return ventaRepository.save(venta);
    }

    public Map<String, Boolean> eliminarVenta(Long idVenta) throws ResourceNotFoundException {
        Venta venta = ventaRepository.findById(idVenta)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró una venta para el ID: " + idVenta));

        // Restaurar el stock de los productos involucrados en la venta
        for (DetalleVenta detalle : venta.getDetallesVenta()) {
            Producto producto = detalle.getProducto();
            producto.disminuirStock(-detalle.getCantidad()); // Aumentar el stock
            productoRepository.save(producto);
        }

        ventaRepository.delete(venta);
        Map<String, Boolean> response = new HashMap<>();
        response.put("eliminado", Boolean.TRUE);
        return response;
    }

}
