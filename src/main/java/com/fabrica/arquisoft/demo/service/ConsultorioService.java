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

    public java.util.List<Consultorio> obtenerTodos() {
        return consultorioRepository.findAll();
    }

    public java.util.Optional<Consultorio> obtenerPorId(Integer idConsultorio) {
        return consultorioRepository.findById(idConsultorio);
    }

    public Consultorio crear(Consultorio consultorio) {
        return consultorioRepository.save(consultorio);
    }

    public Consultorio actualizar(Integer idConsultorio, Consultorio consultorio) {
        Consultorio existente = consultorioRepository.findById(idConsultorio)
                .orElseThrow(() -> new EntityNotFoundException("Consultorio con id " + idConsultorio + " no existe"));
        existente.setNumeroConsultorio(consultorio.getNumeroConsultorio());
        existente.setTipoConsultorio(consultorio.getTipoConsultorio());
        return consultorioRepository.save(existente);
    }

    public void eliminar(Integer idConsultorio) {
        consultorioRepository.deleteById(idConsultorio);
    }

    public Consultorio validarExiste(Integer idConsultorio) {
        return consultorioRepository.findById(idConsultorio)
                .orElseThrow(() -> new EntityNotFoundException("Consultorio con id " + idConsultorio + " no existe"));
    }

}
