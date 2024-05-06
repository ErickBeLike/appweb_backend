package com.api.appweb.dto;

import java.time.LocalDate;

public class EmpleadoDTO {
    private Long idEmpleado;
    private String nombreEmpleado;
    private String apellidoPaEmpleado;
    private String apellidoMaEmpleado;
    private Long idCargo;
    private Long idSexo;
    private LocalDate fechaNacimiento;
    private String direccionEmpleado;
    private String correoEmpleado;
    private String numeroEmpleado;

    public Long getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Long idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public String getApellidoPaEmpleado() {
        return apellidoPaEmpleado;
    }

    public void setApellidoPaEmpleado(String apellidoPaEmpleado) {
        this.apellidoPaEmpleado = apellidoPaEmpleado;
    }

    public String getApellidoMaEmpleado() {
        return apellidoMaEmpleado;
    }

    public void setApellidoMaEmpleado(String apellidoMaEmpleado) {
        this.apellidoMaEmpleado = apellidoMaEmpleado;
    }

    public Long getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Long idCargo) {
        this.idCargo = idCargo;
    }

    public Long getIdSexo() {
        return idSexo;
    }

    public void setIdSexo(Long idSexo) {
        this.idSexo = idSexo;
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

    public String getCorreoEmpleado() {
        return correoEmpleado;
    }

    public void setCorreoEmpleado(String correoEmpleado) {
        this.correoEmpleado = correoEmpleado;
    }

    public String getNumeroEmpleado() {
        return numeroEmpleado;
    }

    public void setNumeroEmpleado(String numeroEmpleado) {
        this.numeroEmpleado = numeroEmpleado;
    }
}
