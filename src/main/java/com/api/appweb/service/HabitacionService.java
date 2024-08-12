package com.api.appweb.service;

import com.api.appweb.entity.Habitacion;
import com.api.appweb.exception.ResourceNotFoundException;
import com.api.appweb.repository.HabitacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HabitacionService {

    @Autowired
    private HabitacionRepository habitacionRepository;

    public List<Habitacion> obtenerTodasLasHabitaciones() {
        return habitacionRepository.findAll();
    }

    public Habitacion buscarHabitacionId(Long idHabitacion) throws ResourceNotFoundException {
        return habitacionRepository.findById(idHabitacion)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró una habitación para el ID: " + idHabitacion));
    }

    public Habitacion agregarHabitacion(Habitacion habitacion) {
        return habitacionRepository.save(habitacion);
    }

    public List<Habitacion> agregarVariasHabitaciones(List<Habitacion> habitaciones) {
        return habitacionRepository.saveAll(habitaciones);
    }

    public Habitacion actualizarHabitacion(Long idHabitacion, Habitacion datosHabitacion) throws ResourceNotFoundException {
        Habitacion habitacion = habitacionRepository.findById(idHabitacion)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró una habitación para el ID: " + idHabitacion));

        habitacion.setHabitacion(datosHabitacion.getHabitacion());
        habitacion.setCupo(datosHabitacion.getCupo());
        habitacion.setPrecioPorNoche(datosHabitacion.getPrecioPorNoche());
        habitacion.setDepositoInicialNoche(datosHabitacion.getDepositoInicialNoche());
        habitacion.setPrecioPorSemana(datosHabitacion.getPrecioPorSemana());
        habitacion.setDepositoInicialSemana(datosHabitacion.getDepositoInicialSemana());
        habitacion.setPrecioPorMes(datosHabitacion.getPrecioPorMes());
        habitacion.setDepositoInicialSemana(datosHabitacion.getDepositoInicialSemana());
        habitacion.setDisponibilidad(datosHabitacion.getDisponibilidad());

        /**
         * Modificación de la fecha dde actualización
         */
        habitacion.setFechaActualizacion(LocalDateTime.now());

        return habitacionRepository.save(habitacion);
    }

    public Map<String, Boolean> eliminarHabitacion(Long idHabitacion) throws ResourceNotFoundException {
        Habitacion habitacion = habitacionRepository.findById(idHabitacion)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró una habitación para el ID: " + idHabitacion));

        habitacionRepository.delete(habitacion);
        Map<String, Boolean> response = new HashMap<>();
        response.put("eliminado", Boolean.TRUE);
        return response;
    }
}
