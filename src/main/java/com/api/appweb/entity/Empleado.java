package com.api.appweb.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "empleados")
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_empleado;
    private String nombre_empleado;
    private String apellidoPa_empleado;
    private String apellidoMa_empleado;

    public int getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(int id_empleado) {
        this.id_empleado = id_empleado;
    }

    public String getNombre_empleado() {
        return nombre_empleado;
    }

    public void setNombre_empleado(String nombre_empleado) {
        this.nombre_empleado = nombre_empleado;
    }

    public String getApellidoPa_empleado() {
        return apellidoPa_empleado;
    }

    public void setApellidoPa_empleado(String apellidoPa_empleado) {
        this.apellidoPa_empleado = apellidoPa_empleado;
    }

    public String getApellidoMa_empleado() {
        return apellidoMa_empleado;
    }

    public void setApellidoMa_empleado(String apellidoMa_empleado) {
        this.apellidoMa_empleado = apellidoMa_empleado;
    }
}
