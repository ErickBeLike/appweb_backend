package com.api.appweb.security.dto;

import javax.validation.constraints.NotBlank;

public class LoginUsuario {
    @NotBlank(message = "nombre de usuario/email obligatorio")
    private String nombreUsuario;
    @NotBlank(message = "contraseña obligatoria")
    private String contrasena;

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
