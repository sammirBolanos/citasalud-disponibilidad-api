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

    public java.util.List<FranjaHoraria> obtenerTodas() {
        return franjaHorariaRepository.findAll();
    }

    public java.util.Optional<FranjaHoraria> obtenerPorId(Short idFranja) {
        return franjaHorariaRepository.findById(idFranja);
    }

    public FranjaHoraria crear(FranjaHoraria franjaHoraria) {
        return franjaHorariaRepository.save(franjaHoraria);
    }

    public FranjaHoraria actualizar(Short idFranja, FranjaHoraria franjaHoraria) {
        FranjaHoraria existente = validarExiste(idFranja);
        existente.setHoraInicio(franjaHoraria.getHoraInicio());
        existente.setHoraFinal(franjaHoraria.getHoraFinal());
        return franjaHorariaRepository.save(existente);
    }

    public void eliminar(Short idFranja) {
        franjaHorariaRepository.deleteById(idFranja);
    }

}
