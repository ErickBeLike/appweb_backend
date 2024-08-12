package com.api.appweb.service;

import com.api.appweb.entity.Producto;
import com.api.appweb.exception.ResourceNotFoundException;
import com.api.appweb.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> obtenerTodosLosProductos() {
        return productoRepository.findAll();
    }

    public Producto buscarProductoId(Long idProducto) throws ResourceNotFoundException {
        return productoRepository.findById(idProducto)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un producto para el ID: " + idProducto));
    }

    public Producto agregarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public List<Producto> agregarVariosProductos(List<Producto> productos) {
        return productoRepository.saveAll(productos);
    }


    public Producto actualizarProducto(Long idProducto, Producto datosProducto) throws ResourceNotFoundException {
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un producto para el ID: " + idProducto));

        // Actualizar nombre y precio del producto
        if (datosProducto.getNombreProducto() != null) {
            producto.setNombreProducto(datosProducto.getNombreProducto());
        }
        if (datosProducto.getPrecioProducto() != null) {
            producto.setPrecioProducto(datosProducto.getPrecioProducto());
        }

        // Actualizar stock si se proporciona una nueva cantidad de stock
        if (datosProducto.getStock() != null) {
            // Asumimos que datosProducto.getStock() contiene el nuevo valor de stock
            producto.setStock(datosProducto.getStock());
        }

        // Actualizar la fecha de actualización
        producto.setFechaActualizacion(LocalDateTime.now());

        return productoRepository.save(producto);
    }

    public Map<String, Boolean> eliminarProducto(Long idProducto) throws ResourceNotFoundException {
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un producto para el ID: " + idProducto));

        productoRepository.delete(producto);
        Map<String, Boolean> response = new HashMap<>();
        response.put("eliminado", Boolean.TRUE);
        return response;
    }
}
