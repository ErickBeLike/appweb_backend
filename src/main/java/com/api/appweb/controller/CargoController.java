package com.api.appweb.controller;

import com.api.appweb.entity.Cargo;
import com.api.appweb.exception.ResourceNotFoundException;
import com.api.appweb.service.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cargos")
public class CargoController {
    @Autowired
    private CargoService cargoService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Cargo> obtenerTodosLosCargos() {
        return cargoService.obtenerTodosLosCargos();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Cargo> buscarCargoId(@PathVariable Long id) throws ResourceNotFoundException {
        Cargo cargo = cargoService.buscarCargoId(id);
        return ResponseEntity.ok().body(cargo);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Cargo agregarCargo(@RequestBody Cargo cargo) throws ResourceNotFoundException {
        return cargoService.agregarCargo(cargo);
    }

    @PostMapping("/batch")
    public ResponseEntity<List<Cargo>> agregarVariosCargos(@RequestBody List<Cargo> cargos) {
        List<Cargo> nuevosCargos = cargoService.agregarVariosCargos(cargos);
        return ResponseEntity.ok(nuevosCargos);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Cargo> actualizarCargo(@PathVariable Long id, @RequestBody Cargo cargo) throws ResourceNotFoundException {
        Cargo cargoActualizado = cargoService.actualizarCargo(id, cargo);
        return ResponseEntity.ok(cargoActualizado);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public Map<String, Boolean> eliminarCargo(@PathVariable Long id) throws ResourceNotFoundException {
        Map<String, Boolean> response = cargoService.eliminarCargo(id);
        return response;
    }
}
