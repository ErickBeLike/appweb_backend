package com.api.appweb.service;

import com.api.appweb.entity.Cargo;
import com.api.appweb.exception.ResourceNotFoundException;
import com.api.appweb.repository.CargoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
