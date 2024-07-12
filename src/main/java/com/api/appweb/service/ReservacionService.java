package com.api.appweb.service;

import com.api.appweb.dto.ReservacionDTO;
import com.api.appweb.entity.Cliente;
import com.api.appweb.entity.Habitacion;
import com.api.appweb.entity.Reservacion;
import com.api.appweb.exception.ResourceNotFoundException;
import com.api.appweb.repository.ClienteRepository;
import com.api.appweb.repository.HabitacionRepository;
import com.api.appweb.repository.ReservacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
}
