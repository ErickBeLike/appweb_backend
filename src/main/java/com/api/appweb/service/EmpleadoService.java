package com.api.appweb.service;

import com.api.appweb.entity.Empleado;
import com.api.appweb.exception.ResourceNotFoundException;
import com.api.appweb.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmpleadoService {
    @Autowired
    private EmpleadoRepository empleadoRepository;

    public List<Empleado> obtenerTodosLosEmpleados() {
        return empleadoRepository.findAll();
    }

    public Empleado buscarEmpleadoId(Long idEmpleado) throws ResourceNotFoundException {
        return empleadoRepository.findById(idEmpleado)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un empleado para el Id: " + idEmpleado));
    }

    public Empleado agregarEmpleado(Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    public List<Empleado> agregarVariosEmpleados(List<Empleado> empleados) {
        return empleadoRepository.saveAll(empleados);
    }

//    public Empleado agregarEmpleado(EmpleadosDTO empleadosDTO)throws ResourceNotFoundException {
//        Cargos cargos = cargosRepository.findById(empleadosDTO.getIdCargo())
//                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un cargo para el ID: " + empleadosDTO.getIdCargo()));
//
//        Sexo sexo = sexoRepository.findById(empleadosDTO.getIdSexo())
//                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un sexo para el ID: " + empleadosDTO.getIdSexo()));
//
//
//        Empleados empleados = new Empleados();
//
//        empleados.setNombreEmpleado(empleadosDTO.getNombreEmpleado());
//        empleados.setApellidoPaEmpleado(empleadosDTO.getApellidoPaEmpleado());
//        empleados.setApellidoMaEmpleado(empleadosDTO.getApellidoMaEmpleado());
//        empleados.setIdCargo(cargos);
//        empleados.setFechaDeNacimiento(empleadosDTO.getFechaDeNacimiento());
//        empleados.setIdSexo(sexo);
//        empleados.setCorreoEmpleado(empleadosDTO.getCorreoEmpleado());
//        empleados.setNumeroEmpleado(empleadosDTO.getNumeroEmpleado());
//        empleados.setDireccionEmpleado(empleadosDTO.getDireccionEmpleado());
//
//        empleadosRepository.save(empleados);
//        return empleados;
//    }

    public Empleado actualizarEmpleado(Long idEmpleado, Empleado datosEmpleado) throws ResourceNotFoundException {
        Empleado empleado = empleadoRepository.findById(idEmpleado)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un empleado para el ID: " + idEmpleado));

        empleado.setNombre_empleado(datosEmpleado.getNombre_empleado());
        empleado.setApellidoPa_empleado(datosEmpleado.getApellidoPa_empleado());
        empleado.setApellidoMa_empleado(datosEmpleado.getApellidoMa_empleado());

        return empleadoRepository.save(empleado);
    }

//    public Empleado actualizarEmpleado(Long idEmpleado, EmpleadosDTO empleadosDTO) throws ResourceNotFoundException {
//        Empleados empleados = empleadosRepository.findById(idEmpleado)
//                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un empleado para el ID: " + idEmpleado));
//
//        Cargos cargo = cargosRepository.findById(empleadosDTO.getIdCargo())
//                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un cargo para el ID: " + empleadosDTO.getIdCargo()));
//
//        Sexo sexo = sexoRepository.findById(empleadosDTO.getIdSexo())
//                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un sexo para el ID: " + empleadosDTO.getIdSexo()));
//
//        empleados.setNombreEmpleado(empleadosDTO.getNombreEmpleado());
//        empleados.setApellidoPaEmpleado(empleadosDTO.getApellidoPaEmpleado());
//        empleados.setApellidoMaEmpleado(empleadosDTO.getApellidoMaEmpleado());
//        empleados.setIdCargo(cargo);
//        empleados.setFechaDeNacimiento(empleadosDTO.getFechaDeNacimiento());
//        empleados.setIdSexo(sexo);
//        empleados.setCorreoEmpleado(empleadosDTO.getCorreoEmpleado());
//        empleados.setNumeroEmpleado(empleadosDTO.getNumeroEmpleado());
//        empleados.setDireccionEmpleado(empleadosDTO.getDireccionEmpleado());
//
//        return empleadosRepository.save(empleados);
//    }

    public Map<String, Boolean> eliminarEmpleado(Long idEmpleado) throws ResourceNotFoundException {
        Empleado empleado = empleadoRepository.findById(idEmpleado)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un empleado para el ID: " + idEmpleado));

        empleadoRepository.delete(empleado);
        Map<String, Boolean> response = new HashMap<>();
        response.put("eliminado", Boolean.TRUE);
        return response;
    }
}
