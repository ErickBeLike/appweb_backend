package com.api.appweb.controller;

import com.api.appweb.entity.Producto;
import com.api.appweb.exception.ResourceNotFoundException;
import com.api.appweb.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public List<Producto> obtenerTodosLosProductos() {
        return productoService.obtenerTodosLosProductos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> buscarProductoId(@PathVariable Long id) throws ResourceNotFoundException {
        Producto producto = productoService.buscarProductoId(id);
        return ResponseEntity.ok().body(producto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Producto> agregarProducto(@RequestBody Producto producto) {
        Producto nuevoProducto = productoService.agregarProducto(producto);
        return ResponseEntity.ok(nuevoProducto);
    }
    /** Esta sección solo es de desarrollo, es la insección de varios productos a la vez */

    @PostMapping("/batch")
    public ResponseEntity<List<Producto>> agregarVariosProductos(@RequestBody List<Producto> productos) {
        List<Producto> nuevosProductos = productoService.agregarVariosProductos(productos);
        return ResponseEntity.ok(nuevosProductos);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @RequestBody Producto producto) throws ResourceNotFoundException {
        Producto productoActualizado = productoService.actualizarProducto(id, producto);
        return ResponseEntity.ok(productoActualizado);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public Map<String, Boolean> eliminarProducto(@PathVariable Long id) throws ResourceNotFoundException {
        Map<String, Boolean> response = productoService.eliminarProducto(id);
        return response;
    }
}
