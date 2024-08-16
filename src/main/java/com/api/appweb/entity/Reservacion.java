package com.api.appweb.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_deposito")
    private Deposito depositoInicial;
    @Column(name = "sub_total")
    private double total;
    @Column(name = "total")
    private double depositoReservacion;
    @Column(name = "precio_por")
    private double precioPor;
    @OneToMany(mappedBy = "reservacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pago> pagos;
    @Column(name = "reservacion_finalizada", nullable = false)
    private boolean reservacionFinalizada = false;

    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }

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

    public Deposito getDepositoInicial() {
        return depositoInicial;
    }

    public void setDepositoInicial(Deposito depositoInicial) {
        this.depositoInicial = depositoInicial;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getDepositoReservacion() {
        return depositoReservacion;
    }

    public void setDepositoReservacion(double depositoReservacion) {
        this.depositoReservacion = depositoReservacion;
    }

    public double getPrecioPor() {
        return precioPor;
    }

    public void setPrecioPor(double precioPor) {
        this.precioPor = precioPor;
    }

    public List<Pago> getPagos() {
        return pagos;
    }

    public void setPagos(List<Pago> pagos) {
        this.pagos = pagos;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public boolean isReservacionFinalizada() {
        return reservacionFinalizada;
    }

    public void setReservacionFinalizada(boolean reservacionFinalizada) {
        this.reservacionFinalizada = reservacionFinalizada;
    }
}
