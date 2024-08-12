package com.api.appweb.controller;

import com.api.appweb.entity.Habitacion;
import com.api.appweb.exception.ResourceNotFoundException;
import com.api.appweb.service.HabitacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/habitaciones")
public class HabitacionController {

    @Autowired
    private HabitacionService habitacionService;

    @GetMapping
    public List<Habitacion> obtenerTodasLasHabitaciones() {
        return habitacionService.obtenerTodasLasHabitaciones();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Habitacion> buscarHabitacionId(@PathVariable Long id) throws ResourceNotFoundException {
        Habitacion habitacion = habitacionService.buscarHabitacionId(id);
        return ResponseEntity.ok().body(habitacion);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Habitacion> agregarHabitacion(@RequestBody Habitacion habitacion) {
        Habitacion nuevaHabitacion = habitacionService.agregarHabitacion(habitacion);
        return ResponseEntity.ok(nuevaHabitacion);
    }

    @PostMapping("/batch")
    public ResponseEntity<List<Habitacion>> agregarVariasHabitaciones(@RequestBody List<Habitacion> habitaciones) {
        List<Habitacion> nuevasHabitaciones = habitacionService.agregarVariasHabitaciones(habitaciones);
        return ResponseEntity.ok(nuevasHabitaciones);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Habitacion> actualizarHabitacion(@PathVariable Long id, @RequestBody Habitacion habitacion) throws ResourceNotFoundException {
        Habitacion habitacionActualizada = habitacionService.actualizarHabitacion(id, habitacion);
        return ResponseEntity.ok(habitacionActualizada);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public Map<String, Boolean> eliminarHabitacion(@PathVariable Long id) throws ResourceNotFoundException {
        Map<String, Boolean> response = habitacionService.eliminarHabitacion(id);
        return response;
    }
}
