package com.fabrica.arquisoft.demo.service;

import org.springframework.stereotype.Service;

import com.fabrica.arquisoft.demo.models.ormModels.Especialidad;
import com.fabrica.arquisoft.demo.repository.EspecialidadRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EspecialidadService {
    private final EspecialidadRepository especialidadRepository;

    public EspecialidadService(EspecialidadRepository especialidadRepository) {
        this.especialidadRepository = especialidadRepository;
    }

    public Especialidad validarExiste(Integer idEspecialidad) {
        return especialidadRepository.findById(idEspecialidad)
                .orElseThrow(() -> new EntityNotFoundException("Especialidad con id " + idEspecialidad + " no existe"));
    }

}
