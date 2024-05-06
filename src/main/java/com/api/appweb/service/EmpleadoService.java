package com.api.appweb.service;

import com.api.appweb.dto.EmpleadoDTO;
import com.api.appweb.entity.Cargo;
import com.api.appweb.entity.Empleado;
import com.api.appweb.entity.Sexo;
import com.api.appweb.exception.ResourceNotFoundException;
import com.api.appweb.repository.CargoRepository;
import com.api.appweb.repository.EmpleadoRepository;
import com.api.appweb.repository.SexoRepository;
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
    @Autowired
    private SexoRepository sexoRepository;

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

            Sexo sexo = sexoRepository.findById(empleadoDTO.getIdSexo())
                    .orElseThrow(() -> new ResourceNotFoundException("No se encontró un sexo para el ID: " + empleadoDTO.getIdSexo()));

            Empleado empleado = new Empleado();
            empleado.setNombreEmpleado(empleadoDTO.getNombreEmpleado());
            empleado.setApellidoPaEmpleado(empleadoDTO.getApellidoPaEmpleado());
            empleado.setApellidoMaEmpleado(empleadoDTO.getApellidoMaEmpleado());
            empleado.setIdCargo(cargo);
            empleado.setFechaNacimiento(empleadoDTO.getFechaNacimiento());
            empleado.setIdSexo(sexo);
            empleado.setCorreoEmpleado(empleadoDTO.getCorreoEmpleado());
            empleado.setNumeroEmpleado(empleadoDTO.getNumeroEmpleado());
            empleado.setDireccionEmpleado(empleadoDTO.getDireccionEmpleado());

            empleados.add(empleado);
        }

        return empleadoRepository.saveAll(empleados);
    }


    public Empleado agregarEmpleado(EmpleadoDTO empleadoDTO)throws ResourceNotFoundException {
        Cargo cargo = cargoRepository.findById(empleadoDTO.getIdCargo())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un cargo para el ID: " + empleadoDTO.getIdCargo()));

        Sexo sexo = sexoRepository.findById(empleadoDTO.getIdSexo())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un sexo para el ID: " + empleadoDTO.getIdSexo()));


        Empleado empleado = new Empleado();

        empleado.setNombreEmpleado(empleadoDTO.getNombreEmpleado());
        empleado.setApellidoPaEmpleado(empleadoDTO.getApellidoPaEmpleado());
        empleado.setApellidoMaEmpleado(empleadoDTO.getApellidoMaEmpleado());
        empleado.setIdCargo(cargo);
        empleado.setFechaNacimiento(empleadoDTO.getFechaNacimiento());
        empleado.setIdSexo(sexo);
        empleado.setCorreoEmpleado(empleadoDTO.getCorreoEmpleado());
        empleado.setNumeroEmpleado(empleadoDTO.getNumeroEmpleado());
        empleado.setDireccionEmpleado(empleadoDTO.getDireccionEmpleado());

        empleadoRepository.save(empleado);
        return empleado;
    }

    public Empleado actualizarEmpleado(Long idEmpleado, EmpleadoDTO empleadoDTO) throws ResourceNotFoundException {
        Empleado empleado = empleadoRepository.findById(idEmpleado)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un empleado para el ID: " + idEmpleado));

        Cargo cargo = cargoRepository.findById(empleadoDTO.getIdCargo())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un cargo para el ID: " + empleadoDTO.getIdCargo()));

        Sexo sexo = sexoRepository.findById(empleadoDTO.getIdSexo())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un sexo para el ID: " + empleadoDTO.getIdSexo()));

        empleado.setNombreEmpleado(empleadoDTO.getNombreEmpleado());
        empleado.setApellidoPaEmpleado(empleadoDTO.getApellidoPaEmpleado());
        empleado.setApellidoMaEmpleado(empleadoDTO.getApellidoMaEmpleado());
        empleado.setIdCargo(cargo);
        empleado.setFechaNacimiento(empleadoDTO.getFechaNacimiento());
        empleado.setIdSexo(sexo);
        empleado.setCorreoEmpleado(empleadoDTO.getCorreoEmpleado());
        empleado.setNumeroEmpleado(empleadoDTO.getNumeroEmpleado());
        empleado.setDireccionEmpleado(empleadoDTO.getDireccionEmpleado());

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
