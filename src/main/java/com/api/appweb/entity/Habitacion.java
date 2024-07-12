package com.api.appweb.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "habitaciones")
public class Habitacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_habitacion")
    private Long idHabitacion;
    @Column(name = "habitacion")
    private String habitacion;
    @Column(name = "cupo")
    private int cupo;
    @Column(name = "precio_noche")
    private double precioPorNoche;
    @Column(name = "deposito_noche")
    private double depositoInicialNoche;
    @Column(name = "precio_semana")
    private double precioPorSemana;
    @Column(name = "deposito_semana")
    private double depositoInicialSemana;
    @Column(name = "precio_mes")
    private double precioPorMes;
    @Column(name = "deposito_mes")
    private double depositoInicialMes;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "disponibilidad")
    private Disponibilidad disponibilidad;

    public Long getIdHabitacion() {
        return idHabitacion;
    }

    public void setIdHabitacion(Long idHabitacion) {
        this.idHabitacion = idHabitacion;
    }

    public String getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(String habitacion) {
        this.habitacion = habitacion;
    }

    public int getCupo() {
        return cupo;
    }

    public void setCupo(int cupo) {
        this.cupo = cupo;
    }

    public double getPrecioPorNoche() {
        return precioPorNoche;
    }

    public void setPrecioPorNoche(double precioPorNoche) {
        this.precioPorNoche = precioPorNoche;
    }

    public double getDepositoInicialNoche() {
        return depositoInicialNoche;
    }

    public void setDepositoInicialNoche(double depositoInicialNoche) {
        this.depositoInicialNoche = depositoInicialNoche;
    }

    public double getPrecioPorSemana() {
        return precioPorSemana;
    }

    public void setPrecioPorSemana(double precioPorSemana) {
        this.precioPorSemana = precioPorSemana;
    }

    public double getDepositoInicialSemana() {
        return depositoInicialSemana;
    }

    public void setDepositoInicialSemana(double depositoInicialSemana) {
        this.depositoInicialSemana = depositoInicialSemana;
    }

    public double getPrecioPorMes() {
        return precioPorMes;
    }

    public void setPrecioPorMes(double precioPorMes) {
        this.precioPorMes = precioPorMes;
    }

    public double getDepositoInicialMes() {
        return depositoInicialMes;
    }

    public void setDepositoInicialMes(double depositoInicialMes) {
        this.depositoInicialMes = depositoInicialMes;
    }

    public Disponibilidad getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(Disponibilidad disponibilidad) {
        this.disponibilidad = disponibilidad;
    }
}
