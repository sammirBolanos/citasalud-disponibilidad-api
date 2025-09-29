package com.fabrica.arquisoft.demo.service;

import org.springframework.stereotype.Service;

import com.fabrica.arquisoft.demo.models.ormModels.Profesional;
import com.fabrica.arquisoft.demo.repository.ProfesionalRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProfesionalService {

    private final ProfesionalRepository profesionalRepository;

    public ProfesionalService(ProfesionalRepository profesionalRepository) {
        this.profesionalRepository = profesionalRepository;
    }

    public Profesional validarExiste(Integer idProfesional) {
        return profesionalRepository.findById(idProfesional)
                .orElseThrow(() -> new EntityNotFoundException("Profesional con id " + idProfesional + " no existe"));
    }
}
