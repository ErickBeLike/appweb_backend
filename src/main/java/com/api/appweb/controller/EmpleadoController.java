package com.api.appweb.controller;

import com.api.appweb.entity.Empleado;
import com.api.appweb.exception.ResourceNotFoundException;
import com.api.appweb.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/empleado")
public class EmpleadoController {
    @Autowired
    private EmpleadoService empleadoService;

    @GetMapping
    public List<Empleado> obtenerTodosLosEmpleado() {
        return empleadoService.obtenerTodosLosEmpleados();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empleado> buscarEmpleadoId(@PathVariable Long id) throws ResourceNotFoundException {
        Empleado empleado = empleadoService.buscarEmpleadoId(id);
        return ResponseEntity.ok().body(empleado);
    }

    @PostMapping
    public Empleado agregarEmpleado(@RequestBody Empleado empleado) throws ResourceNotFoundException {
        return empleadoService.agregarEmpleado(empleado);
    }

    @PostMapping("/batch")
    public ResponseEntity<List<Empleado>> agregarVariosEmpleados(@RequestBody List<Empleado> empleados) {
        List<Empleado> nuevosEmpleados = empleadoService.agregarVariosEmpleados(empleados);
        return ResponseEntity.ok(nuevosEmpleados);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Empleado> actualizarEmpleado(@PathVariable Long id, @RequestBody Empleado empleado)
            throws ResourceNotFoundException {
        Empleado updatedEmpleado = empleadoService.actualizarEmpleado(id, empleado);
        return ResponseEntity.ok(updatedEmpleado);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> eliminarEmpleado(@PathVariable Long id) throws ResourceNotFoundException {
        empleadoService.eliminarEmpleado(id);
        Map<String, Boolean> response = Map.of("eliminado", true);
        return response;
    }
}
