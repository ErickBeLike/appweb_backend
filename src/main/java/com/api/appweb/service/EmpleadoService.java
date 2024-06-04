package com.api.appweb.service;

import com.api.appweb.dto.EmpleadoDTO;
import com.api.appweb.entity.Cargo;
import com.api.appweb.entity.Empleado;
import com.api.appweb.entity.Persona;
import com.api.appweb.exception.ResourceNotFoundException;
import com.api.appweb.repository.CargoRepository;
import com.api.appweb.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmpleadoService {
    @Autowired
    private EmpleadoRepository empleadoRepository;
    @Autowired
    private CargoRepository cargoRepository;

    public List<Empleado> obtenerTodosLosEmpleados() {
        return empleadoRepository.findAll();
    }

    public Empleado buscarEmpleadoId(Long idEmpleado) throws ResourceNotFoundException {
        return empleadoRepository.findById(idEmpleado)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un empleado para el Id: " + idEmpleado));
    }

    public List<Empleado> agregarVariosEmpleados(List<EmpleadoDTO> empleadoDTOs) throws ResourceNotFoundException {
        List<Empleado> empleados = new ArrayList<>();

        for (EmpleadoDTO empleadoDTO : empleadoDTOs) {
            Cargo cargo = cargoRepository.findById(empleadoDTO.getIdCargo())
                    .orElseThrow(() -> new ResourceNotFoundException("No se encontró un cargo para el ID: " + empleadoDTO.getIdCargo()));

            // Crear la entidad Persona a partir de los datos del DTO
            Persona persona = empleadoDTO.getPersona();

            // Crear el empleado con la persona y el cargo asociados
            Empleado empleado = new Empleado();
            empleado.setPersona(persona);
            empleado.setIdCargo(cargo);
            empleado.setFechaNacimiento(empleadoDTO.getFechaNacimiento());
            empleado.setSexo(empleadoDTO.getSexo());
            empleado.setDireccionEmpleado(empleadoDTO.getDireccionEmpleado());
            empleado.setHorarioEntrada(empleadoDTO.getHorarioEntrada());
            empleado.setHorarioSalida(empleadoDTO.getHorarioSalida());
            empleado.setDiasLaborales(empleadoDTO.getDiasLaborales());

            empleados.add(empleado);
        }

        return empleadoRepository.saveAll(empleados);
    }

    public Empleado agregarEmpleado(EmpleadoDTO empleadoDTO) throws ResourceNotFoundException {
        Cargo cargo = cargoRepository.findById(empleadoDTO.getIdCargo())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un cargo para el ID: " + empleadoDTO.getIdCargo()));

        // Crear la entidad Persona a partir de los datos del DTO
        Persona persona = empleadoDTO.getPersona();

        // Crear el empleado con la persona y el cargo asociados
        Empleado empleado = new Empleado();
        empleado.setPersona(persona);
        empleado.setIdCargo(cargo);
        empleado.setFechaNacimiento(empleadoDTO.getFechaNacimiento());
        empleado.setSexo(empleadoDTO.getSexo());
        empleado.setDireccionEmpleado(empleadoDTO.getDireccionEmpleado());
        empleado.setHorarioEntrada(empleadoDTO.getHorarioEntrada());
        empleado.setHorarioSalida(empleadoDTO.getHorarioSalida());
        empleado.setDiasLaborales(empleadoDTO.getDiasLaborales());

        return empleadoRepository.save(empleado);
    }

    public Empleado actualizarEmpleado(Long idEmpleado, EmpleadoDTO empleadoDTO) throws ResourceNotFoundException {
        Empleado empleado = empleadoRepository.findById(idEmpleado)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un empleado para el ID: " + idEmpleado));

        Cargo cargo = cargoRepository.findById(empleadoDTO.getIdCargo())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un cargo para el ID: " + empleadoDTO.getIdCargo()));

        // Actualizar los datos del empleado con los datos del DTO
        empleado.setPersona(empleadoDTO.getPersona());
        empleado.setIdCargo(cargo);
        empleado.setFechaNacimiento(empleadoDTO.getFechaNacimiento());
        empleado.setSexo(empleadoDTO.getSexo());
        empleado.setDireccionEmpleado(empleadoDTO.getDireccionEmpleado());
        empleado.setHorarioEntrada(empleadoDTO.getHorarioEntrada());
        empleado.setHorarioSalida(empleadoDTO.getHorarioSalida());
        empleado.setDiasLaborales(empleadoDTO.getDiasLaborales());

        return empleadoRepository.save(empleado);
    }

    public Map<String, Boolean> eliminarEmpleado(Long idEmpleado) throws ResourceNotFoundException {
        Empleado empleado = empleadoRepository.findById(idEmpleado)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un empleado para el ID: " + idEmpleado));

        empleadoRepository.delete(empleado);

        Map<String, Boolean> response = new HashMap<>();
        response.put("eliminado", Boolean.TRUE);
        return response;
    }
}
