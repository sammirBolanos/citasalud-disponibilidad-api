package com.fabrica.arquisoft.demo.service;

import org.springframework.stereotype.Service;

import com.fabrica.arquisoft.demo.models.ormModels.Consultorio;
import com.fabrica.arquisoft.demo.repository.ConsultorioRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ConsultorioService {
private final ConsultorioRepository consultorioRepository;

    public ConsultorioService(ConsultorioRepository consultorioRepository) {
        this.consultorioRepository = consultorioRepository;
    }

    public Consultorio validarExiste(Integer idConsultorio) {
        return consultorioRepository.findById(idConsultorio)
                .orElseThrow(() -> new EntityNotFoundException("Consultorio con id " + idConsultorio + " no existe"));
    }

}
