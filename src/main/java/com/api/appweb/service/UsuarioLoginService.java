//package com.api.appweb.service;
//
//import com.api.appweb.entity.Usuario;
//import com.api.appweb.exception.ResourceNotFoundException;
//import com.api.appweb.repository.UsuarioRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UsuarioLoginService {
//
//    @Autowired
//    private UsuarioRepository usuarioRepository;
//
//    public String iniciarSesion(String nombreUsuario, String contrasena) throws ResourceNotFoundException {
//        Usuario usuario = usuarioRepository.findByNombreUsuario(nombreUsuario)
//                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un usuario con el nombre: " + nombreUsuario));
//
//        // Verificar la contraseña
//        if (!usuario.getContrasenaUsuario().equals(contrasena)) {
//            throw new ResourceNotFoundException("La contraseña proporcionada no es válida para el usuario con nombre: " + nombreUsuario);
//        }
//
//        // Si el inicio de sesión fue exitoso, devolver un mensaje de éxito con el nombre del usuario
//        return "Inicio de sesión exitoso para el usuario: " + usuario.getNombreUsuario();
//    }
//}
