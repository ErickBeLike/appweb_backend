package com.api.appweb.security.service;

import com.api.appweb.dto.UsuarioRespuesta;
import com.api.appweb.exception.CustomException;
import com.api.appweb.security.dto.JwtDTO;
import com.api.appweb.security.dto.LoginUsuario;
import com.api.appweb.security.dto.NuevoUsuario;
import com.api.appweb.security.entity.Rol;
import com.api.appweb.security.entity.Usuario;
import com.api.appweb.security.enums.RolNombre;
import com.api.appweb.security.jwt.JwtProvider;
import com.api.appweb.security.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RolService rolService;

    @Autowired
    JwtProvider jwtProvider;

    public Optional<Usuario> getByNombreUsuario(String nombreUsuario){
        return usuarioRepository.findByNombreUsuario(nombreUsuario);
    }

    public boolean existsByNombreUsuario(String nombreUsuario){
        return usuarioRepository.existsByNombreUsuario(nombreUsuario);
    }

    public JwtDTO login(LoginUsuario loginUsuario){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginUsuario.getNombreUsuario(), loginUsuario.getContrasena()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);

        String nombreUsuario = loginUsuario.getNombreUsuario();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        return new JwtDTO(jwt, "Bearer", nombreUsuario, authorities);
    }

    public UsuarioRespuesta save(NuevoUsuario nuevoUsuario) {
        if (usuarioRepository.existsByNombreUsuario(nuevoUsuario.getNombreUsuario())) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "ese nombre de usuario ya existe");
        }
        String contrasena = nuevoUsuario.getContrasena().trim();
        if (contrasena.isEmpty()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "contraseña inválida");
        }
        Usuario usuario = new Usuario(nuevoUsuario.getNombreUsuario(),
                passwordEncoder.encode(contrasena));
        usuario.setContrasenaUncripted(contrasena); // Guardar la contraseña desencriptada

        Set<Rol> roles = new HashSet<>();
        roles.add(rolService.getByRolNombre(RolNombre.ROLE_USER).get());
        if (nuevoUsuario.getRoles().contains("admin")) {
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());
        }
        usuario.setRoles(roles);
        usuarioRepository.save(usuario);
        return new UsuarioRespuesta(usuario.getNombreUsuario() + " ha sido creado", usuario);
    }


    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario obtenerUsuarioPorId(Integer id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "No se encontró un usuario para el ID: " + id));
    }

    public Usuario actualizarUsuario(Integer id, NuevoUsuario nuevoUsuario) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "No se encontró un usuario para el ID: " + id));

        String contrasena = nuevoUsuario.getContrasena().trim();
        if (contrasena.isEmpty()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "contraseña inválida");
        }

        usuario.setNombreUsuario(nuevoUsuario.getNombreUsuario());
        usuario.setContrasena(passwordEncoder.encode(contrasena));
        usuario.setContrasenaUncripted(contrasena);

        Set<Rol> roles = new HashSet<>();
        roles.add(rolService.getByRolNombre(RolNombre.ROLE_USER).get());
        if (nuevoUsuario.getRoles().contains("admin")) {
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());
        }
        usuario.setRoles(roles);

        usuarioRepository.save(usuario);

        return usuario;
    }

    public Map<String, Boolean> eliminarUsuario(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "No se encontró un usuario para el ID: " + id));

        usuarioRepository.delete(usuario);

        Map<String, Boolean> response = new HashMap<>();
        response.put("eliminado", Boolean.TRUE);
        return response;
    }
}
