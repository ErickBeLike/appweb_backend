package com.api.appweb.service;

import com.api.appweb.entity.Cargo;
import com.api.appweb.exception.ResourceNotFoundException;
import com.api.appweb.repository.CargoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CargoService {
    @Autowired
    private CargoRepository cargoRepository;


    public List<Cargo> obtenerTodosLosCargos() {
        return cargoRepository.findAll();
    }

    public Cargo buscarCargoId(Long idCargo) throws ResourceNotFoundException {
        return cargoRepository.findById(idCargo)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un cargo para el ID: " + idCargo));
    }

    public Cargo agregarCargo(Cargo cargo) {
        return cargoRepository.save(cargo);
    }

    public List<Cargo> agregarVariosCargos(List<Cargo> cargos) {
        return cargoRepository.saveAll(cargos);
    }

    public Cargo actualizarCargo(Long idCargo, Cargo datosCargo) throws ResourceNotFoundException {
        Cargo cargo = cargoRepository.findById(idCargo)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un cargo para el ID: " + idCargo));

        // Actualiza los datos del cargo
        cargo.setNombreCargo(datosCargo.getNombreCargo());
        cargo.setDescripcionCargo(datosCargo.getDescripcionCargo());

        // Guarda los cambios en la base de datos
        cargoRepository.save(cargo);

        return cargo;
    }

    public Map<String, Boolean> eliminarCargo(Long idCargo) throws ResourceNotFoundException {
        Cargo cargo = cargoRepository.findById(idCargo)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un cargo para el ID: " + idCargo));

        // Elimina el cliente y la persona asociada en cascada
        cargoRepository.delete(cargo);

        Map<String, Boolean> response = new HashMap<>();
        response.put("eliminado", Boolean.TRUE);
        return response;
    }
}
