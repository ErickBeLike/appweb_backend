package com.api.appweb.util;

import com.api.appweb.security.entity.Rol;
import com.api.appweb.security.enums.RolNombre;
import com.api.appweb.security.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * MUY IMPORTANTE: ESTA CLASE SÓLO SE EJECUTARÁ UNA VEZ PARA CREAR LOS ROLES.
 * UNA VEZ CREADOS SE DEBERÁ ELIMINAR O BIEN COMENTAR EL CÓDIGO
 */

@Component
public class CreateRoles implements CommandLineRunner {

    @Autowired
    RolService rolService;

    @Override
    public void run(String... args) throws Exception {
        /**
         * Sección a descomentar para crear roles
         */
/*
        Rol rolAdmin = new Rol(RolNombre.ROLE_ADMIN);
         Rol rolUser = new Rol(RolNombre.ROLE_USER);
         rolService.save(rolAdmin);
         rolService.save(rolUser);
*/
    }
}
