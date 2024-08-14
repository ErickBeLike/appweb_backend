package com.api.appweb.controller;

import com.api.appweb.dto.DepositoDTO;
import com.api.appweb.dto.PagoDTO;
import com.api.appweb.dto.ReservacionDTO;
import com.api.appweb.entity.Deposito;
import com.api.appweb.entity.Pago;
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

    // Agregar un nuevo pago
    @PostMapping("/{idReservacion}/pagos")
    public ResponseEntity<Pago> agregarPago(@PathVariable Long idReservacion, @RequestBody PagoDTO pagoDTO) throws ResourceNotFoundException {
        Pago nuevoPago = reservacionService.agregarPago(idReservacion, pagoDTO);
        return ResponseEntity.ok(nuevoPago);
    }

    // Actualizar un pago existente
    @PutMapping("/pagos/{idPago}")
    public ResponseEntity<Pago> actualizarPago(@PathVariable Long idPago, @RequestBody PagoDTO pagoDTO) throws ResourceNotFoundException {
        Pago pagoActualizado = reservacionService.actualizarPago(idPago, pagoDTO);
        return ResponseEntity.ok(pagoActualizado);
    }

    // Obtener todos los pagos para una reservación específica
    @GetMapping("/{idReservacion}/pagos")
    public ResponseEntity<List<Pago>> obtenerPagosPorReservacion(@PathVariable Long idReservacion) throws ResourceNotFoundException {
        List<Pago> pagos = reservacionService.obtenerPagosPorReservacion(idReservacion);
        return ResponseEntity.ok(pagos);
    }

    // Agregar un nuevo depósito (si se necesita)
    @PostMapping("/{idReservacion}/depositos")
    public ResponseEntity<Deposito> agregarDeposito(@PathVariable Long idReservacion, @RequestBody DepositoDTO depositoDTO) throws ResourceNotFoundException {
        Deposito nuevoDeposito = reservacionService.agregarDeposito(idReservacion, depositoDTO);
        return ResponseEntity.ok(nuevoDeposito);
    }

    // Actualizar un depósito existente
    @PutMapping("/{idReservacion}/depositos")
    public ResponseEntity<Deposito> actualizarDeposito(@PathVariable Long idReservacion, @RequestBody DepositoDTO depositoDTO) throws ResourceNotFoundException {
        Deposito depositoActualizado = reservacionService.actualizarDeposito(idReservacion, depositoDTO);
        return ResponseEntity.ok(depositoActualizado);
    }

    // Obtener el depósito de una reservación específica
    @GetMapping("/{idReservacion}/depositos")
    public ResponseEntity<Deposito> obtenerDepositoPorReservacion(@PathVariable Long idReservacion) throws ResourceNotFoundException {
        Deposito deposito = reservacionService.obtenerDepositoPorReservacion(idReservacion);
        return ResponseEntity.ok(deposito);
    }
}
