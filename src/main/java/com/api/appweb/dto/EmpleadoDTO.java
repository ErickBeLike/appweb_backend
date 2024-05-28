package com.api.appweb.dto;

import com.api.appweb.entity.Persona;
import com.api.appweb.entity.Sexo;

import java.time.LocalDate;
import java.util.List;

public class EmpleadoDTO {
    private Long idEmpleado;
    private Persona persona; // Cambiar por PersonaDTO
    private Sexo sexo;
    private Long idCargo;
    private LocalDate fechaNacimiento;
    private String direccionEmpleado;
    private String horarioEntrada;
    private String horarioSalida;
    private List<String> diasLaborales;

    public Long getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Long idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public Long getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Long idCargo) {
        this.idCargo = idCargo;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getDireccionEmpleado() {
        return direccionEmpleado;
    }

    public void setDireccionEmpleado(String direccionEmpleado) {
        this.direccionEmpleado = direccionEmpleado;
    }

    public String getHorarioEntrada() {
        return horarioEntrada;
    }

    public void setHorarioEntrada(String horarioEntrada) {
        this.horarioEntrada = horarioEntrada;
    }

    public String getHorarioSalida() {
        return horarioSalida;
    }

    public void setHorarioSalida(String horarioSalida) {
        this.horarioSalida = horarioSalida;
    }

    public List<String> getDiasLaborales() {
        return diasLaborales;
    }

    public void setDiasLaborales(List<String> diasLaborales) {
        this.diasLaborales = diasLaborales;
    }
}
