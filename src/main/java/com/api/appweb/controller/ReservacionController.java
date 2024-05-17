package com.api.appweb.controller;

import com.api.appweb.dto.ReservacionDTO;
import com.api.appweb.entity.Reservacion;
import com.api.appweb.exception.ResourceNotFoundException;
import com.api.appweb.service.ReservacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reservaciones")
public class ReservacionController {

    @Autowired
    private ReservacionService reservacionService;

    @GetMapping
    public List<Reservacion> obtenerTodasLasReservaciones() {
        return reservacionService.obtenerTodasLasReservaciones();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservacion> buscarReservacionPorId(@PathVariable Long id) throws ResourceNotFoundException {
        Reservacion reservacion = reservacionService.buscarReservacionPorId(id);
        return ResponseEntity.ok().body(reservacion);
    }

    @PostMapping
    public ResponseEntity<Reservacion> agregarReservacion(@RequestBody ReservacionDTO reservacionDTO) throws ResourceNotFoundException {
        Reservacion nuevaReservacion = reservacionService.agregarReservacion(reservacionDTO);
        return ResponseEntity.ok(nuevaReservacion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reservacion> actualizarReservacion(@PathVariable Long id, @RequestBody ReservacionDTO reservacionDTO) throws ResourceNotFoundException {
        Reservacion reservacionActualizada = reservacionService.actualizarReservacion(id, reservacionDTO);
        return ResponseEntity.ok(reservacionActualizada);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> eliminarReservacion(@PathVariable Long id) throws ResourceNotFoundException {
        Map<String, Boolean> response = reservacionService.eliminarReservacion(id);
        return response;
    }
}
