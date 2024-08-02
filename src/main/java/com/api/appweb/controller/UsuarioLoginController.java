//package com.api.appweb.controller;
//
//import com.api.appweb.exception.ResourceNotFoundException;
//import com.api.appweb.service.UsuarioLoginService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/login")
//public class UsuarioLoginController {
//
//    @Autowired
//    private UsuarioLoginService usuarioLoginService;
//
//    @PostMapping
//    public ResponseEntity<Map<String, String>> iniciarSesion(@RequestBody UsuarioLoginRequest request) throws ResourceNotFoundException {
//        String mensaje = usuarioLoginService.iniciarSesion(request.getNombreUsuario(), request.getContrasena());
//        Map<String, String> response = new HashMap<>();
//        response.put("mensaje", mensaje);
//        return ResponseEntity.ok(response);
//    }
//
//    public static class UsuarioLoginRequest {
//        private String nombreUsuario;
//        private String contrasena;
//
//        public UsuarioLoginRequest() {}
//
//        public UsuarioLoginRequest(String nombreUsuario, String contrasena) {
//            this.nombreUsuario = nombreUsuario;
//            this.contrasena = contrasena;
//        }
//
//        // Getters y setters
//        public String getNombreUsuario() {
//            return nombreUsuario;
//        }
//
//        public void setNombreUsuario(String nombreUsuario) {
//            this.nombreUsuario = nombreUsuario;
//        }
//
//        public String getContrasena() {
//            return contrasena;
//        }
//
//        public void setContrasena(String contrasena) {
//            this.contrasena = contrasena;
//        }
//    }
//}
//
