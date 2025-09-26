package com.fabrica.arquisoft.demo.service;

import org.springframework.stereotype.Service;

import com.fabrica.arquisoft.demo.models.ormModels.FranjaHoraria;
import com.fabrica.arquisoft.demo.repository.FranjaHorariaRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class FranjaHorariaService {

    private final FranjaHorariaRepository franjaHorariaRepository;

    public FranjaHorariaService(FranjaHorariaRepository franjaHorariaRepository) {
        this.franjaHorariaRepository = franjaHorariaRepository;
    }

    public FranjaHoraria validarExiste(Short idFranja) {
        return franjaHorariaRepository.findById(idFranja)
                .orElseThrow(() -> new EntityNotFoundException("Franja horaria con id " + idFranja + " no existe"));
    }

}
