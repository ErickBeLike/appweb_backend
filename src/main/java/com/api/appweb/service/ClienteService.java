package com.api.appweb.service;

import com.api.appweb.entity.Cliente;
import com.api.appweb.entity.Persona;
import com.api.appweb.exception.ResourceNotFoundException;
import com.api.appweb.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> obtenerTodosLosClientes() {
        return clienteRepository.findAll();
    }

    public Cliente buscarClienteId(Long idCliente) throws ResourceNotFoundException {
        return clienteRepository.findById(idCliente)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un cliente para el ID: " + idCliente));
    }

    public Cliente agregarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public List<Cliente> agregarVariosClientes(List<Cliente> clientes) {
        return clienteRepository.saveAll(clientes);
    }

    public Cliente actualizarCliente(Long idCliente, Cliente datosCliente) throws ResourceNotFoundException {
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un cliente para el ID: " + idCliente));

        Persona persona = cliente.getPersona(); // Obtiene la persona asociada al cliente
        Persona datosPersona = datosCliente.getPersona(); // Obtiene los datos de la persona del cliente actualizados

        // Actualiza los datos de la persona
        persona.setNombre(datosPersona.getNombre());
        persona.setApellidoPaterno(datosPersona.getApellidoPaterno());
        persona.setApellidoMaterno(datosPersona.getApellidoMaterno());
        persona.setTelefono(datosPersona.getTelefono());
        persona.setCorreo(datosPersona.getCorreo());

        // Guarda los cambios en la base de datos
        /**
         * Método para modificar la fecha dde la actualización del cliente
         */
        cliente.setFechaActualizacion(LocalDateTime.now());

        // Guarda los cambios en la base de datos
        clienteRepository.save(cliente);

        return cliente;
    }

    public Map<String, Boolean> eliminarCliente(Long idCliente) throws ResourceNotFoundException {
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un cliente para el ID: " + idCliente));

        // Elimina el cliente y la persona asociada en cascada
        clienteRepository.delete(cliente);

        Map<String, Boolean> response = new HashMap<>();
        response.put("eliminado", Boolean.TRUE);
        return response;
    }

}
