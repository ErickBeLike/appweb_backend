package com.api.appweb.controller;

import com.api.appweb.entity.Producto;
import com.api.appweb.exception.ResourceNotFoundException;
import com.api.appweb.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Producto> buscarProductoPorId(@PathVariable Long id) throws ResourceNotFoundException {
        Producto producto = productoService.buscarProducto(id);
        return ResponseEntity.ok().body(producto);
    }

    @PostMapping
    public ResponseEntity<Producto> agregarProducto(@RequestBody Producto producto) {
        Producto nuevoProducto = productoService.agregarProducto(producto);
        return ResponseEntity.ok(nuevoProducto);
    }

    @PostMapping("/batch")
    public ResponseEntity<List<Producto>> agregarVariosProductos(@RequestBody List<Producto> productos) {
        List<Producto> nuevosProductos = productoService.agregarVariosProductos(productos);
        return ResponseEntity.ok(nuevosProductos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @RequestBody Producto producto) throws ResourceNotFoundException {
        Producto productoActualizado = productoService.actualizarProducto(id, producto);
        return ResponseEntity.ok(productoActualizado);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> eliminarProducto(@PathVariable Long id) throws ResourceNotFoundException {
        Map<String, Boolean> response = productoService.eliminarProducto(id);
        return response;
    }
}
