package com.api.appweb.controller;

import com.api.appweb.dto.EmpleadoDTO;
import com.api.appweb.entity.Empleado;
import com.api.appweb.exception.ResourceNotFoundException;
import com.api.appweb.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {
    @Autowired
    private EmpleadoService empleadoService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Empleado> obtenerTodosLosEmpleado() {
        return empleadoService.obtenerTodosLosEmpleados();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Empleado> buscarEmpleadoId(@PathVariable Long id) throws ResourceNotFoundException {
        Empleado empleado = empleadoService.buscarEmpleadoId(id);
        return ResponseEntity.ok().body(empleado);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Empleado agregarEmpleado(@RequestBody EmpleadoDTO empleadoDTO) throws ResourceNotFoundException {
        return empleadoService.agregarEmpleado(empleadoDTO);
    }

    @PostMapping("/batch")
    public ResponseEntity<List<Empleado>> agregarVariosEmpleados(@RequestBody List<EmpleadoDTO> empleadoDTOs) {
        try {
            List<Empleado> nuevosEmpleados = empleadoService.agregarVariosEmpleados(empleadoDTOs);
            return ResponseEntity.ok(nuevosEmpleados);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Empleado> actualizarEmpleado(@PathVariable Long id, @RequestBody EmpleadoDTO empleadoDTO)
            throws ResourceNotFoundException {
        Empleado updatedEmpleado = empleadoService.actualizarEmpleado(id, empleadoDTO);
        return ResponseEntity.ok(updatedEmpleado);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public Map<String, Boolean> eliminarEmpleado(@PathVariable Long id) throws ResourceNotFoundException {
        empleadoService.eliminarEmpleado(id);
        Map<String, Boolean> response = Map.of("eliminado", true);
        return response;
    }
}
