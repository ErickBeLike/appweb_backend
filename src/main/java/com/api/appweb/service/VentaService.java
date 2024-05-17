package com.api.appweb.service;

import com.api.appweb.dto.ProductoCantidadDTO;
import com.api.appweb.dto.VentaDTO;
import com.api.appweb.dto.ProductoDetalleDTO;
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
import java.util.stream.Collectors;

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

    public Venta buscarVentaId(Long idVenta) throws ResourceNotFoundException {
        return ventaRepository.findById(idVenta)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró una venta para el ID: " + idVenta));
    }

    public Venta agregarVenta(VentaDTO ventaDTO) throws ResourceNotFoundException {
        Venta venta = new Venta();
        venta.setIdEmpleado(empleadoRepository.findById(ventaDTO.getIdEmpleado())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un empleado para el ID: " + ventaDTO.getIdEmpleado())));

        Map<Producto, Integer> cantidadesProducto = new HashMap<>();
        Map<Producto, Double> preciosUnitarios = new HashMap<>();
        for (ProductoCantidadDTO productoCantidadDTO : ventaDTO.getProductos()) {
            Producto producto = productoRepository.findById(productoCantidadDTO.getIdProducto())
                    .orElseThrow(() -> new ResourceNotFoundException("No se encontró un producto para el ID: " + productoCantidadDTO.getIdProducto()));
            cantidadesProducto.put(producto, productoCantidadDTO.getCantidad());
            preciosUnitarios.put(producto, producto.getPrecioProducto());
        }

        venta.setCantidadesProducto(cantidadesProducto);
        venta.setPreciosUnitarios(preciosUnitarios);

        double total = calcularTotalVenta(cantidadesProducto, preciosUnitarios);
        venta.setTotal(total);

        venta.setFechaVenta(LocalDateTime.now());

        return ventaRepository.save(venta);
    }

    private double calcularTotalVenta(Map<Producto, Integer> cantidadesProducto, Map<Producto, Double> preciosUnitarios) {
        return cantidadesProducto.entrySet().stream()
                .mapToDouble(entry -> entry.getValue() * preciosUnitarios.get(entry.getKey()))
                .sum();
    }

    public Venta actualizarVenta(Long idVenta, VentaDTO ventaDTO) throws ResourceNotFoundException {
        Venta venta = ventaRepository.findById(idVenta)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró una venta para el ID: " + idVenta));

        venta.setIdEmpleado(empleadoRepository.findById(ventaDTO.getIdEmpleado())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un empleado para el ID: " + ventaDTO.getIdEmpleado())));

        Map<Producto, Integer> cantidadesProducto = new HashMap<>();
        Map<Producto, Double> preciosUnitarios = new HashMap<>();
        for (ProductoCantidadDTO productoCantidadDTO : ventaDTO.getProductos()) {
            Producto producto = productoRepository.findById(productoCantidadDTO.getIdProducto())
                    .orElseThrow(() -> new ResourceNotFoundException("No se encontró un producto para el ID: " + productoCantidadDTO.getIdProducto()));
            cantidadesProducto.put(producto, productoCantidadDTO.getCantidad());
            preciosUnitarios.put(producto, producto.getPrecioProducto());
        }

        venta.setCantidadesProducto(cantidadesProducto);
        venta.setPreciosUnitarios(preciosUnitarios);

        double total = calcularTotalVenta(cantidadesProducto, preciosUnitarios);
        venta.setTotal(total);

        venta.setFechaVenta(LocalDateTime.now());

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
