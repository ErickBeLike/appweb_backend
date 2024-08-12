package com.api.appweb.dto;

import com.api.appweb.security.entity.Usuario;

public class UsuarioRespuesta {
    private String mensaje;
    private Usuario usuario;

    public UsuarioRespuesta(String mensaje, Usuario usuario) {
        this.mensaje = mensaje;
        this.usuario = usuario;
    }

    // Getters y Setters
    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
