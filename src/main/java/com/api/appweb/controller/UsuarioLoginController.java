package com.api.appweb.controller;

import com.api.appweb.exception.ResourceNotFoundException;
import com.api.appweb.service.UsuarioLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
public class UsuarioLoginController {

    @Autowired
    private UsuarioLoginService usuarioLoginService;

    @PostMapping
    public ResponseEntity<String> iniciarSesion(@RequestBody UsuarioLoginRequest request) throws ResourceNotFoundException {
        String mensaje = usuarioLoginService.iniciarSesion(request.getCorreo(), request.getContrasena());
        return ResponseEntity.ok(mensaje);
    }

    public static class UsuarioLoginRequest {
        private String correo;
        private String contrasena;

        public UsuarioLoginRequest() {}

        public UsuarioLoginRequest(String correo, String contrasena) {
            this.correo = correo;
            this.contrasena = contrasena;
        }

        // Getters y setters
        public String getCorreo() {
            return correo;
        }

        public void setCorreo(String correo) {
            this.correo = correo;
        }

        public String getContrasena() {
            return contrasena;
        }

        public void setContrasena(String contrasena) {
            this.contrasena = contrasena;
        }
    }
}
