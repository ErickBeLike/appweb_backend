package com.api.appweb.service;

import com.api.appweb.dto.PagoDTO;
import com.api.appweb.dto.ReservacionDTO;
import com.api.appweb.entity.Cliente;
import com.api.appweb.entity.Habitacion;
import com.api.appweb.entity.Pago;
import com.api.appweb.entity.Reservacion;
import com.api.appweb.exception.ResourceNotFoundException;
import com.api.appweb.repository.ClienteRepository;
import com.api.appweb.repository.HabitacionRepository;
import com.api.appweb.repository.PagoRepository;
import com.api.appweb.repository.ReservacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.api.appweb.entity.Disponibilidad.DISPONIBLE;
import static com.api.appweb.entity.Disponibilidad.OCUPADA;

@Service
public class ReservacionService {

    @Autowired
    private ReservacionRepository reservacionRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private HabitacionRepository habitacionRepository;

    @Autowired
    private PagoRepository pagoRepository;

    public List<Reservacion> obtenerTodasLasReservaciones() {
        return reservacionRepository.findAll();
    }

    public Reservacion buscarReservacionPorId(Long idReservacion) throws ResourceNotFoundException {
        return reservacionRepository.findById(idReservacion)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró una reservación para el ID: " + idReservacion));
    }

    public Reservacion agregarReservacion(ReservacionDTO reservacionDTO) throws ResourceNotFoundException {
        // Obtener cliente y habitación
        Cliente cliente = clienteRepository.findById(reservacionDTO.getIdCliente())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un cliente para el ID: " + reservacionDTO.getIdCliente()));

        Habitacion habitacion = habitacionRepository.findById(reservacionDTO.getIdHabitacion())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró una habitación para el ID: " + reservacionDTO.getIdHabitacion()));

        // Verificar disponibilidad de la habitación
        if (!habitacion.getDisponibilidad().equals(DISPONIBLE)) {
            throw new IllegalArgumentException("La habitación no está disponible.");
        }

        // Crear la reservación y asignar valores
        Reservacion reservacion = new Reservacion();
        reservacion.setIdCliente(cliente);
        reservacion.setFechaInicio(reservacionDTO.getFechaInicio());
        reservacion.setTiempoReservacion(reservacionDTO.getTiempoReservacion());
        reservacion.setTipoReservacion(reservacionDTO.getTipoReservacion());
        reservacion.setIdHabitacion(habitacion);

        // Calcular el total y la fecha final
        double total = 0.0;
        LocalDate fechaFinal = reservacion.getFechaInicio();
        double depositoInicial = 0.0;

        switch (reservacionDTO.getTipoReservacion()) {
            case NOCHE:
                total = habitacion.getPrecioPorNoche() * reservacionDTO.getTiempoReservacion();
                depositoInicial = habitacion.getDepositoInicialNoche();
                fechaFinal = fechaFinal.plusDays(reservacionDTO.getTiempoReservacion());
                break;
            case SEMANA:
                total = habitacion.getPrecioPorSemana() * reservacionDTO.getTiempoReservacion();
                depositoInicial = habitacion.getDepositoInicialSemana();
                fechaFinal = fechaFinal.plusWeeks(reservacionDTO.getTiempoReservacion());
                break;
            case MES:
                total = habitacion.getPrecioPorMes() * reservacionDTO.getTiempoReservacion();
                depositoInicial = habitacion.getDepositoInicialMes();
                fechaFinal = fechaFinal.plusMonths(reservacionDTO.getTiempoReservacion());
                break;
            default:
                throw new IllegalArgumentException("Tipo de reservación no válido");
        }

        // Asignar total, fecha final y depósito inicial
        reservacion.setTotal(total);
        reservacion.setFechaFinal(fechaFinal);
        reservacion.setDepositoInicial(depositoInicial);
        reservacion.setPrecioPor(total / reservacionDTO.getTiempoReservacion());

        // Agregar pagos a la reservación
        List<Pago> pagos = new ArrayList<>();
        double montoPorPago = reservacion.getPrecioPor();
        for (int i = 1; i <= reservacionDTO.getTiempoReservacion(); i++) {
            Pago pago = new Pago();
            pago.setNumeroPago(i);
            pago.setMonto(montoPorPago);
            pago.setPagado(false); // Inicialmente, el pago no está hecho
            pago.setReservacion(reservacion);
            pagos.add(pago);
        }
        reservacion.setPagos(pagos);

        // Marcar habitación como ocupada
        habitacion.setDisponibilidad(OCUPADA);
        habitacionRepository.save(habitacion);

        // Guardar la reservación
        return reservacionRepository.save(reservacion);
    }

    public Reservacion actualizarReservacion(Long idReservacion, ReservacionDTO reservacionDTO) throws ResourceNotFoundException {
        // Obtener la reservación existente
        Reservacion reservacion = reservacionRepository.findById(idReservacion)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró una reservación para el ID: " + idReservacion));

        // Obtener las habitaciones involucradas
        Habitacion habitacionActual = reservacion.getIdHabitacion();
        Habitacion nuevaHabitacion = habitacionRepository.findById(reservacionDTO.getIdHabitacion())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró una habitación para el ID: " + reservacionDTO.getIdHabitacion()));

        // Verificar disponibilidad de la nueva habitación
        if (!habitacionActual.getIdHabitacion().equals(nuevaHabitacion.getIdHabitacion())) {
            if (nuevaHabitacion.getDisponibilidad().equals(OCUPADA)) {
                throw new IllegalArgumentException("La nueva habitación no está disponible.");
            }

            habitacionActual.setDisponibilidad(DISPONIBLE);
            habitacionRepository.save(habitacionActual);

            nuevaHabitacion.setDisponibilidad(OCUPADA);
            habitacionRepository.save(nuevaHabitacion);
        }

        // Asignar los nuevos valores a la reservación
        reservacion.setIdCliente(clienteRepository.findById(reservacionDTO.getIdCliente())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un cliente para el ID: " + reservacionDTO.getIdCliente())));
        reservacion.setFechaInicio(reservacionDTO.getFechaInicio());
        reservacion.setTiempoReservacion(reservacionDTO.getTiempoReservacion());
        reservacion.setTipoReservacion(reservacionDTO.getTipoReservacion());
        reservacion.setIdHabitacion(nuevaHabitacion);

        // Calcular el total y la fecha final
        double total = 0.0;
        LocalDate fechaFinal = reservacion.getFechaInicio();
        double depositoInicial = 0.0;

        switch (reservacionDTO.getTipoReservacion()) {
            case NOCHE:
                total = nuevaHabitacion.getPrecioPorNoche() * reservacionDTO.getTiempoReservacion();
                depositoInicial = nuevaHabitacion.getDepositoInicialNoche();
                fechaFinal = fechaFinal.plusDays(reservacionDTO.getTiempoReservacion());
                break;
            case SEMANA:
                total = nuevaHabitacion.getPrecioPorSemana() * reservacionDTO.getTiempoReservacion();
                depositoInicial = nuevaHabitacion.getDepositoInicialSemana();
                fechaFinal = fechaFinal.plusWeeks(reservacionDTO.getTiempoReservacion());
                break;
            case MES:
                total = nuevaHabitacion.getPrecioPorMes() * reservacionDTO.getTiempoReservacion();
                depositoInicial = nuevaHabitacion.getDepositoInicialMes();
                fechaFinal = fechaFinal.plusMonths(reservacionDTO.getTiempoReservacion());
                break;
            default:
                throw new IllegalArgumentException("Tipo de reservación no válido");
        }

        // Asignar total, fecha final y depósito inicial
        reservacion.setTotal(total);
        reservacion.setFechaFinal(fechaFinal);
        reservacion.setDepositoInicial(depositoInicial);
        reservacion.setPrecioPor(total / reservacionDTO.getTiempoReservacion());

        // Obtener y eliminar pagos si la duración se ha reducido
        List<Pago> pagosExistentes = reservacion.getPagos();
        List<Pago> pagosAEliminar = new ArrayList<>();

        if (reservacionDTO.getTiempoReservacion() < pagosExistentes.size()) {
            for (int i = reservacionDTO.getTiempoReservacion(); i < pagosExistentes.size(); i++) {
                pagosAEliminar.add(pagosExistentes.get(i));
            }
            pagosExistentes.removeAll(pagosAEliminar); // Eliminar pagos de la lista en la entidad
            pagoRepository.deleteAll(pagosAEliminar); // Eliminar pagos de la base de datos
        }

        // Actualizar pagos existentes
        for (int i = 0; i < pagosExistentes.size(); i++) {
            Pago pagoExistente = pagosExistentes.get(i);
            pagoExistente.setMonto(reservacion.getPrecioPor());
            pagoRepository.save(pagoExistente);
        }

        // Crear nuevos pagos si la duración se ha extendido
        List<Pago> nuevosPagos = new ArrayList<>();
        for (int i = pagosExistentes.size(); i < reservacionDTO.getTiempoReservacion(); i++) {
            Pago nuevoPago = new Pago();
            nuevoPago.setNumeroPago(i + 1);
            nuevoPago.setMonto(reservacion.getPrecioPor());
            nuevoPago.setPagado(false); // Inicialmente, el pago no está hecho
            nuevoPago.setReservacion(reservacion);
            nuevosPagos.add(nuevoPago);
        }

        // Agregar nuevos pagos a la lista de pagos y guardarlos
        if (!nuevosPagos.isEmpty()) {
            reservacion.getPagos().addAll(nuevosPagos); // Agregar nuevos pagos a la lista de pagos en la reservación
            pagoRepository.saveAll(nuevosPagos);
        }

        // Guardar la reservación actualizada
        return reservacionRepository.save(reservacion);
    }

    public Map<String, Boolean> eliminarReservacion(Long idReservacion) throws ResourceNotFoundException {
        Reservacion reservacion = reservacionRepository.findById(idReservacion)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró una reservación para el ID: " + idReservacion));

        Habitacion habitacion = reservacion.getIdHabitacion();
        habitacion.setDisponibilidad(DISPONIBLE);
        habitacionRepository.save(habitacion);

        reservacionRepository.delete(reservacion);
        Map<String, Boolean> response = new HashMap<>();
        response.put("eliminado", Boolean.TRUE);
        return response;
    }

    public Pago agregarPago(Long idReservacion, PagoDTO pagoDTO) throws ResourceNotFoundException {
        Reservacion reservacion = reservacionRepository.findById(idReservacion)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró una reservación para el ID: " + idReservacion));

        Pago nuevoPago = new Pago();
        nuevoPago.setNumeroPago(pagoDTO.getNumeroPago());
        nuevoPago.setMonto(pagoDTO.getMonto());
        nuevoPago.setPagado(pagoDTO.isPagado());
        nuevoPago.setReservacion(reservacion);

        return pagoRepository.save(nuevoPago);
    }

    public Pago actualizarPago(Long idPago, PagoDTO pagoDTO) throws ResourceNotFoundException {
        Pago pago = pagoRepository.findById(idPago)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un pago para el ID: " + idPago));

        // Actualiza todos los campos necesarios
        if (pagoDTO.getNumeroPago() != 0) { // Validación si es necesario
            pago.setNumeroPago(pagoDTO.getNumeroPago());
        }
        if (pagoDTO.getMonto() > 0) { // Validación si es necesario
            pago.setMonto(pagoDTO.getMonto());
        }
        pago.setPagado(pagoDTO.isPagado());

        return pagoRepository.save(pago);
    }


    public List<Pago> obtenerPagosPorReservacion(Long idReservacion) throws ResourceNotFoundException {
        Reservacion reservacion = reservacionRepository.findById(idReservacion)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró una reservación para el ID: " + idReservacion));

        return reservacion.getPagos();
    }
}
