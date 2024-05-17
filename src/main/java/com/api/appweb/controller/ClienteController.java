package com.api.appweb.controller;

import com.api.appweb.entity.Cliente;
import com.api.appweb.exception.ResourceNotFoundException;
import com.api.appweb.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public List<Cliente> obtenerTodosLosClientes() {
        return clienteService.obtenerTodosLosClientes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarClienteId(@PathVariable Long id) throws ResourceNotFoundException {
        Cliente cliente = clienteService.buscarClienteId(id);
        return ResponseEntity.ok().body(cliente);
    }

    @PostMapping
    public ResponseEntity<Cliente> agregarCliente(@RequestBody Cliente cliente) {
        Cliente nuevoCliente = clienteService.agregarCliente(cliente);
        return ResponseEntity.ok(nuevoCliente);
    }

    @PostMapping("/batch")
    public ResponseEntity<List<Cliente>> agregarVariosClientes(@RequestBody List<Cliente> clientes) {
        List<Cliente> nuevosClientes = clienteService.agregarVariosClientes(clientes);
        return ResponseEntity.ok(nuevosClientes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizarCliente(@PathVariable Long id, @RequestBody Cliente cliente) throws ResourceNotFoundException {
        Cliente clienteActualizado = clienteService.actualizarCliente(id, cliente);
        return ResponseEntity.ok(clienteActualizado);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> eliminarCliente(@PathVariable Long id) throws ResourceNotFoundException {
        Map<String, Boolean> response = clienteService.eliminarCliente(id);
        return response;
    }
}
