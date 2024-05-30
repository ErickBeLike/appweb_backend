package com.api.appweb.service;

import com.api.appweb.dto.ProductoCantidadDTO;
import com.api.appweb.dto.VentaDTO;
import com.api.appweb.entity.*;
import com.api.appweb.exception.ResourceNotFoundException;
import com.api.appweb.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    private EmpleadoRepository empleadoRepository;

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
        venta.setIdEmpleado(empleadoRepository.findById(ventaDTO.getIdEmpleado())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un empleado para el ID: " + ventaDTO.getIdEmpleado())));

        List<DetalleVenta> detallesVenta = new ArrayList<>();
        double totalVenta = 0.0;

        for (ProductoCantidadDTO productoCantidadDTO : ventaDTO.getProductos()) {
            Producto producto = productoRepository.findById(productoCantidadDTO.getIdProducto())
                    .orElseThrow(() -> new ResourceNotFoundException("No se encontró un producto para el ID: " + productoCantidadDTO.getIdProducto()));

            int cantidad = productoCantidadDTO.getCantidad();
            double precioUnitario = producto.getPrecioProducto();
            double subtotal = cantidad * precioUnitario;
            totalVenta += subtotal;

            DetalleVenta detalleVenta = new DetalleVenta();
            detalleVenta.setVenta(venta);
            detalleVenta.setProducto(producto);
            detalleVenta.setCantidad(cantidad);
            detalleVenta.setPrecioUnitario(precioUnitario);
            detalleVenta.setSubtotal(subtotal);

            detallesVenta.add(detalleVenta);
        }

        venta.setDetallesVenta(detallesVenta);
        venta.setTotal(totalVenta);
        venta.setFechaVenta(LocalDateTime.now());

        return ventaRepository.save(venta);
    }

    public Venta actualizarVenta(Long idVenta, VentaDTO ventaDTO) throws ResourceNotFoundException {
        Venta venta = ventaRepository.findById(idVenta)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró una venta para el ID: " + idVenta));

        venta.setIdEmpleado(empleadoRepository.findById(ventaDTO.getIdEmpleado())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un empleado para el ID: " + ventaDTO.getIdEmpleado())));

        List<ProductoCantidadDTO> nuevosDetallesVenta = ventaDTO.getProductos();

        // Eliminar los detalles de venta que no están presentes en la solicitud PUT
        venta.getDetallesVenta().removeIf(detalleVenta ->
                nuevosDetallesVenta.stream().noneMatch(nuevoDetalle ->
                        nuevoDetalle.getIdProducto().equals(detalleVenta.getProducto().getIdProducto())));

        // Actualizar la cantidad de los detalles de venta existentes
        for (DetalleVenta detalleVenta : venta.getDetallesVenta()) {
            for (ProductoCantidadDTO nuevoDetalle : nuevosDetallesVenta) {
                if (detalleVenta.getProducto().getIdProducto().equals(nuevoDetalle.getIdProducto())) {
                    detalleVenta.setCantidad(nuevoDetalle.getCantidad());
                    break;
                }
            }
        }

        // Agregar nuevos detalles de venta que no están presentes en la base de datos
        for (ProductoCantidadDTO nuevoDetalle : nuevosDetallesVenta) {
            boolean encontrado = false;
            for (DetalleVenta detalleVenta : venta.getDetallesVenta()) {
                if (detalleVenta.getProducto().getIdProducto().equals(nuevoDetalle.getIdProducto())) {
                    encontrado = true;
                    break;
                }
            }
            if (!encontrado) {
                Producto producto = productoRepository.findById(nuevoDetalle.getIdProducto())
                        .orElseThrow(() -> new ResourceNotFoundException("No se encontró un producto para el ID: " + nuevoDetalle.getIdProducto()));

                DetalleVenta nuevoDetalleVenta = new DetalleVenta();
                nuevoDetalleVenta.setVenta(venta);
                nuevoDetalleVenta.setProducto(producto);
                nuevoDetalleVenta.setCantidad(nuevoDetalle.getCantidad());
                nuevoDetalleVenta.setPrecioUnitario(producto.getPrecioProducto());
                nuevoDetalleVenta.setSubtotal(producto.getPrecioProducto() * nuevoDetalle.getCantidad());

                venta.getDetallesVenta().add(nuevoDetalleVenta);
            }
        }

        // Calcular el nuevo total de la venta
        double totalVenta = venta.getDetallesVenta().stream()
                .mapToDouble(detalleVenta -> detalleVenta.getCantidad() * detalleVenta.getPrecioUnitario())
                .sum();
        venta.setTotal(totalVenta);
        venta.setFechaVenta(LocalDateTime.now());

        // Guardar la venta actualizada en la base de datos
        return ventaRepository.save(venta);
    }


    public Map<String, Boolean> eliminarVenta(Long idVenta) throws ResourceNotFoundException {
        Venta venta = ventaRepository.findById(idVenta)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró una venta para el ID: " + idVenta));

        ventaRepository.delete(venta);
        Map<String, Boolean> response = new HashMap<>();
        response.put("eliminado", Boolean.TRUE);
        return response;
    }
}
