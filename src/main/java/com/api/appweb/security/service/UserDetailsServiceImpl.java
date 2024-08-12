package com.api.appweb.security.service;

import com.api.appweb.security.entity.Usuario;
import com.api.appweb.security.entity.UsuarioPrincipal;
import com.api.appweb.security.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(()-> new UsernameNotFoundException("ese usuario no existe"));
        return UsuarioPrincipal.build(usuario);
    }
}
