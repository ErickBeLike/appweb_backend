package com.api.appweb.service;

import com.api.appweb.entity.Usuario;
import com.api.appweb.exception.ResourceNotFoundException;
import com.api.appweb.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarUsuarioId(Long idUsuario) throws ResourceNotFoundException {
        return usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un usuario para el ID: " + idUsuario));
    }

    public Usuario agregarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> agregarVariosUsuarios(List<Usuario> usuarios) {
        return usuarioRepository.saveAll(usuarios);
    }

    public Usuario actualizarUsuario(Long idUsuario, Usuario datosUsuario) throws ResourceNotFoundException {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un usuario para el ID: " + idUsuario));

        usuario.setRol(datosUsuario.getRol());
        usuario.setNombreUsuario(datosUsuario.getNombreUsuario());
        usuario.setCorreoUsuario(datosUsuario.getCorreoUsuario());
        usuario.setContrasenaUsuario(datosUsuario.getContrasenaUsuario());
        return usuarioRepository.save(usuario);
    }

    public void eliminarUsuario(Long idUsuario) throws ResourceNotFoundException {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un usuario para el ID: " + idUsuario));

        usuarioRepository.delete(usuario);
    }
}
