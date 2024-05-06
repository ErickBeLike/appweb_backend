package com.api.appweb.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "empleados")
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empleado")
    private Long idEmpleado;
    @Column(name = "nombre")
    private String nombreEmpleado;
    @Column(name = "apellido_paterno")
    private String apellidoPaEmpleado;
    @Column(name = "apellido_materno")
    private String apellidoMaEmpleado;
    @ManyToOne
    @JoinColumn(name = "id_cargo")
    private Cargo idCargo;
    @ManyToOne
    @JoinColumn(name = "id_sexo")
    private Sexo idSexo;
    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;
    @Column(name = "direccion")
    private String direccionEmpleado;
    @Column(name = "correo")
    private String correoEmpleado;
    @Column(name = "numero")
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

    public Cargo getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Cargo idCargo) {
        this.idCargo = idCargo;
    }

    public Sexo getIdSexo() {
        return idSexo;
    }

    public void setIdSexo(Sexo idSexo) {
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
