package com.api.appweb.security.entity;


import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario", nullable = false)
    private int idUsuario;
    @NotNull
    @Column(name = "nombre_usuario", nullable = false, unique = true)
    private String nombreUsuario;
    @NotNull
    @Column(name = "contrasena", nullable = false)
    private String contrasena;
    @Column(name = "contrasena_uncripted")
    private String contrasenaUncripted;
    @NotNull
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_rol", joinColumns = @JoinColumn(name = "usuario_id"),
    inverseJoinColumns = @JoinColumn(name = "rol_id"))
    private Set<Rol> roles = new HashSet<>();

    public Usuario() {
    }

    public Usuario(@NotNull String nombreUsuario, @NotNull String contrasena) {
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
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

    public String getContrasenaUncripted() {
        return contrasenaUncripted;
    }

    public void setContrasenaUncripted(String contrasenaUncripted) {
        this.contrasenaUncripted = contrasenaUncripted;
    }

    public Set<Rol> getRoles() {
        return roles;
    }

    public void setRoles(Set<Rol> roles) {
        this.roles = roles;
    }
}
