package com.api.appweb.service;
import com.api.appweb.dto.ProductoCantidadDTO;
import com.api.appweb.dto.VentaDTO;
import com.api.appweb.entity.Empleado;
import com.api.appweb.entity.Producto;
import com.api.appweb.entity.Venta;
import com.api.appweb.exception.ResourceNotFoundException;
import com.api.appweb.repository.EmpleadoRepository;
import com.api.appweb.repository.ProductoRepository;
import com.api.appweb.repository.VentaRepository;
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

    public List<Venta> obtenerTodasLasVentas() {
        return ventaRepository.findAll();
    }

    public Venta buscarVenta(Long idVenta) throws ResourceNotFoundException {
        return ventaRepository.findById(idVenta)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró una venta para el ID: " + idVenta));
    }

    public Venta agregarVenta(VentaDTO ventaDTO) throws ResourceNotFoundException {
        Venta venta = new Venta();
        venta.setIdEmpleado(empleadoRepository.findById(ventaDTO.getIdEmpleado())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un empleado para el ID: " + ventaDTO.getIdEmpleado())));

        Map<Producto, Integer> cantidadesProducto = new HashMap<>();
        for (ProductoCantidadDTO productoCantidadDTO : ventaDTO.getProductos()) {
            Producto producto = productoRepository.findById(productoCantidadDTO.getIdProducto())
                    .orElseThrow(() -> new ResourceNotFoundException("No se encontró un producto para el ID: " + productoCantidadDTO.getIdProducto()));
            cantidadesProducto.put(producto, productoCantidadDTO.getCantidad()); // Usamos la cantidad proporcionada en el DTO
        }

        venta.setCantidadesProducto(cantidadesProducto);

        // Ahora calculamos el total utilizando las cantidades reales de los productos
        double total = calcularTotalVenta(cantidadesProducto);
        venta.setTotal(total);

        venta.setFecha_venta(LocalDateTime.now());

        return ventaRepository.save(venta);
    }

    private double calcularTotalVenta(Map<Producto, Integer> cantidadesProducto) {
        double total = 0.0;
        for (Map.Entry<Producto, Integer> entry : cantidadesProducto.entrySet()) {
            Producto producto = entry.getKey();
            int cantidad = entry.getValue();
            total += producto.getPrecioProducto() * cantidad;
        }
        return total;
    }

    public Venta actualizarVenta(Long idVenta, VentaDTO ventaDTO) throws ResourceNotFoundException {
        Venta venta = ventaRepository.findById(idVenta)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró una venta para el ID: " + idVenta));

        venta.setIdEmpleado(empleadoRepository.findById(ventaDTO.getIdEmpleado())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un empleado para el ID: " + ventaDTO.getIdEmpleado())));

        // Obtenemos la lista de productos de la venta actual
        Map<Producto, Integer> cantidadesProducto = venta.getCantidadesProducto();

        // Lista para almacenar los productos que deben eliminarse de la venta
        List<Producto> productosAEliminar = new ArrayList<>();

        // Iteramos sobre los productos de la venta actual
        for (Map.Entry<Producto, Integer> entry : cantidadesProducto.entrySet()) {
            Producto producto = entry.getKey();
            Integer cantidad = entry.getValue();

            // Verificamos si el producto está presente en el DTO de actualización
            boolean encontrado = false;
            for (ProductoCantidadDTO productoCantidadDTO : ventaDTO.getProductos()) {
                if (producto.getIdProducto().equals(productoCantidadDTO.getIdProducto())) {
                    // Si el producto está presente, actualizamos su cantidad con el valor proporcionado
                    cantidadesProducto.put(producto, productoCantidadDTO.getCantidad());
                    encontrado = true;
                    break;
                }
            }
            // Si el producto no está presente, lo marcamos para eliminar de la venta
            if (!encontrado) {
                productosAEliminar.add(producto);
            }
        }

        // Eliminamos los productos que deben eliminarse de la venta
        for (Producto producto : productosAEliminar) {
            cantidadesProducto.remove(producto);
        }

        // Agregamos los nuevos productos a la venta
        for (ProductoCantidadDTO productoCantidadDTO : ventaDTO.getProductos()) {
            Producto productoNuevo = productoRepository.findById(productoCantidadDTO.getIdProducto())
                    .orElseThrow(() -> new ResourceNotFoundException("No se encontró un producto para el ID: " + productoCantidadDTO.getIdProducto()));

            if (!cantidadesProducto.containsKey(productoNuevo)) {
                cantidadesProducto.put(productoNuevo, productoCantidadDTO.getCantidad());
            }
        }

        // Calculamos el total utilizando las cantidades actualizadas
        double total = calcularTotalVenta(cantidadesProducto);
        venta.setTotal(total);
        venta.setFecha_venta(LocalDateTime.now());

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
