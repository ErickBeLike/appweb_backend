package com.api.appweb.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservaciones")
public class Reservacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reservacion")
    private Long idReservacion;
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente idCliente;
    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;
    @Column(name = "tiempo_reservación")
    private int tiempoReservacion;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "tipo_reservacion")
    private TipoReservacion tipoReservacion;
    @Column(name = "fecha_final")
    private LocalDate fechaFinal;
    @ManyToOne
    @JoinColumn(name = "id_habitacion")
    private Habitacion idHabitacion;
    @Column(name = "deposito_inicial")
    private double depositoInicial;
    @Column(name = "total")
    private double total;

    public Long getIdReservacion() {
        return idReservacion;
    }

    public void setIdReservacion(Long idReservacion) {
        this.idReservacion = idReservacion;
    }

    public Cliente getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Cliente idCliente) {
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

    public LocalDate getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(LocalDate fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public Habitacion getIdHabitacion() {
        return idHabitacion;
    }

    public void setIdHabitacion(Habitacion idHabitacion) {
        this.idHabitacion = idHabitacion;
    }

    public double getDepositoInicial() {
        return depositoInicial;
    }

    public void setDepositoInicial(double depositoInicial) {
        this.depositoInicial = depositoInicial;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
