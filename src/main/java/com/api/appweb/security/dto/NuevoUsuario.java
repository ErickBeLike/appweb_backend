package com.api.appweb.security.dto;

import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

public class NuevoUsuario {
    @NotBlank(message = "nombre de usuario obligatorio")
    private String nombreUsuario;
    @NotBlank
    private String contrasena;
    private Set<String> roles;

    public NuevoUsuario(String nombreUsuario, String contrasena, Set<String> roles) {
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.roles = roles;
    }

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

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
