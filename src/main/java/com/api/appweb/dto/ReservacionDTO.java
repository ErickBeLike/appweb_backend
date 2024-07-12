package com.api.appweb.dto;

import com.api.appweb.entity.TipoReservacion;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ReservacionDTO {
    private Long idReservacion;
    private Long idCliente;
    private LocalDate fechaInicio;
    private int tiempoReservacion;
    private TipoReservacion tipoReservacion;
    private Long idHabitacion;

    public Long getIdReservacion() {
        return idReservacion;
    }

    public void setIdReservacion(Long idReservacion) {
        this.idReservacion = idReservacion;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public int getTiempoReservacion() {
        return tiempoReservacion;
    }

    public void setTiempoReservacion(int tiempoReservacion) {
        this.tiempoReservacion = tiempoReservacion;
    }

    public TipoReservacion getTipoReservacion() {
        return tipoReservacion;
    }

    public void setTipoReservacion(TipoReservacion tipoReservacion) {
        this.tipoReservacion = tipoReservacion;
    }

    public Long getIdHabitacion() {
        return idHabitacion;
    }

    public void setIdHabitacion(Long idHabitacion) {
        this.idHabitacion = idHabitacion;
    }
}
