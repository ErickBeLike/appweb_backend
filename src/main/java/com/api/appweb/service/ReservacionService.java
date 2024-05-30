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
import java.time.LocalDateTime;
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
        Cliente cliente = clienteRepository.findById(reservacionDTO.getIdCliente())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un cliente para el ID: " + reservacionDTO.getIdCliente()));

        Habitacion habitacion = habitacionRepository.findById(reservacionDTO.getIdHabitacion())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró una habitación para el ID: " + reservacionDTO.getIdHabitacion()));

        if (!habitacion.getDisponibilidad().equals(DISPONIBLE)) {
            throw new IllegalArgumentException("La habitación no está disponible.");
        }

        Reservacion reservacion = new Reservacion();
        reservacion.setIdCliente(cliente);
        reservacion.setFechaInicio(reservacionDTO.getFechaInicio());
        reservacion.setDias(reservacionDTO.getDias());
        reservacion.setFechaFinal(reservacion.getFechaInicio().plusDays(reservacion.getDias()));
        reservacion.setIdHabitacion(habitacion);
        reservacion.setTotal(habitacion.getPrecioDia() * reservacion.getDias());

        habitacion.setDisponibilidad(OCUPADA);
        habitacionRepository.save(habitacion);

        return reservacionRepository.save(reservacion);
    }

    public Reservacion actualizarReservacion(Long idReservacion, ReservacionDTO reservacionDTO) throws ResourceNotFoundException {
        Reservacion reservacion = reservacionRepository.findById(idReservacion)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró una reservación para el ID: " + idReservacion));

        Habitacion habitacionActual = reservacion.getIdHabitacion();
        Habitacion nuevaHabitacion = habitacionRepository.findById(reservacionDTO.getIdHabitacion())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró una habitación para el ID: " + reservacionDTO.getIdHabitacion()));

        if (!habitacionActual.getIdHabitacion().equals(nuevaHabitacion.getIdHabitacion())) {
            if (nuevaHabitacion.getDisponibilidad().equals(OCUPADA)) {
                throw new IllegalArgumentException("La nueva habitación no está disponible.");
            }

            habitacionActual.setDisponibilidad(DISPONIBLE);
            habitacionRepository.save(habitacionActual);

            nuevaHabitacion.setDisponibilidad(OCUPADA);
            habitacionRepository.save(nuevaHabitacion);
        }

        reservacion.setIdCliente(clienteRepository.findById(reservacionDTO.getIdCliente())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un cliente para el ID: " + reservacionDTO.getIdCliente())));
        reservacion.setFechaInicio(reservacionDTO.getFechaInicio());
        reservacion.setDias(reservacionDTO.getDias());
        reservacion.setFechaFinal(reservacion.getFechaInicio().plusDays(reservacion.getDias()));
        reservacion.setIdHabitacion(nuevaHabitacion);
        reservacion.setTotal(nuevaHabitacion.getPrecioDia() * reservacion.getDias());

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
