package com.api.appweb.controller;

import com.api.appweb.dto.VentaDTO;
import com.api.appweb.entity.Venta;
import com.api.appweb.exception.ResourceNotFoundException;
import com.api.appweb.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @GetMapping
    public List<Venta> obtenerTodasLasVentas() {
        return ventaService.obtenerTodasLasVentas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venta> buscarVentaId(@PathVariable Long id) throws ResourceNotFoundException {
        Venta venta = ventaService.buscarVentaId(id);
        return ResponseEntity.ok().body(venta);
    }

    @PostMapping
    public ResponseEntity<Venta> agregarVenta(@RequestBody VentaDTO ventaDTO) throws ResourceNotFoundException {
        Venta nuevaVenta = ventaService.agregarVenta(ventaDTO);
        return ResponseEntity.ok(nuevaVenta);
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Venta> actualizarVenta(@PathVariable Long id, @RequestBody VentaDTO ventaDTO) throws ResourceNotFoundException {
//        Venta ventaActualizada = ventaService.actualizarVenta(id, ventaDTO);
//        return ResponseEntity.ok(ventaActualizada);
//    }


    @DeleteMapping("/{id}")
    public Map<String, Boolean> eliminarVenta(@PathVariable Long id) throws ResourceNotFoundException {
        Map<String, Boolean> response = ventaService.eliminarVenta(id);
        return response;
    }
}
