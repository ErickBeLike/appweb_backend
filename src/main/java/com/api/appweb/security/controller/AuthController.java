package com.api.appweb.security.controller;

import com.api.appweb.dto.UsuarioRespuesta;
import com.api.appweb.exception.CustomException;
import com.api.appweb.security.dto.JwtDTO;
import com.api.appweb.security.dto.LoginUsuario;
import com.api.appweb.security.dto.NuevoUsuario;
import com.api.appweb.security.entity.Usuario;
import com.api.appweb.security.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    UsuarioService usuarioService;

    @PostMapping("/nuevo")
    public ResponseEntity<UsuarioRespuesta> nuevo(@Valid @RequestBody NuevoUsuario nuevoUsuario) {
        UsuarioRespuesta respuesta = usuarioService.save(nuevoUsuario);
        return ResponseEntity.ok(respuesta);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtDTO> login(@Valid @RequestBody LoginUsuario loginUsuario){
        return ResponseEntity.ok(usuarioService.login(loginUsuario));
    }

    @GetMapping("/get")
    public ResponseEntity<List<Usuario>> obtenerTodosLosUsuarios() {
        List<Usuario> usuarios = usuarioService.obtenerTodosLosUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable Integer id) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Integer id, @Valid @RequestBody NuevoUsuario nuevoUsuario) {
        Usuario usuarioActualizado = usuarioService.actualizarUsuario(id, nuevoUsuario);
        return ResponseEntity.ok(usuarioActualizado);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Boolean>> eliminarUsuario(@PathVariable Integer id) {
        Map<String, Boolean> response = usuarioService.eliminarUsuario(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtDTO> refresh(@RequestBody JwtDTO jwtDTO) throws ParseException {
        return ResponseEntity.ok(usuarioService.refresh(jwtDTO));
    }
}
