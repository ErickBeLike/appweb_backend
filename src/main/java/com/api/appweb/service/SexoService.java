package com.api.appweb.service;

import com.api.appweb.entity.Sexo;
import com.api.appweb.exception.ResourceNotFoundException;
import com.api.appweb.repository.SexoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SexoService {
    @Autowired
    private SexoRepository sexoRepository;

    public List<Sexo> obtenerTodosLosSexos() {
        return sexoRepository.findAll();
    }

    public Sexo buscarSexoPorId(Long idSexo) throws ResourceNotFoundException {
        return sexoRepository.findById(idSexo)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un sexo para el ID: " + idSexo));
    }

    public Sexo agregarSexo(Sexo sexo) {
        return sexoRepository.save(sexo);
    }

    public List<Sexo> agregarVariosSexos(List<Sexo> sexos) {
        return sexoRepository.saveAll(sexos);
    }
}
