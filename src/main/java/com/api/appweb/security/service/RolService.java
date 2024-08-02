package com.api.appweb.security.service;

import com.api.appweb.security.entity.Rol;
import com.api.appweb.security.enums.RolNombre;
import com.api.appweb.security.repository.RolRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class RolService {
    @Autowired
    RolRepository rolRepository;

    public Optional<Rol> getByRolNombre(RolNombre rolNombre) {
        return rolRepository.findByRolNombre(rolNombre);
    }

    public void save(Rol rol){
        rolRepository.save(rol);
    }
}
