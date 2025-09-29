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

    public java.util.List<Profesional> obtenerTodos() {
        return profesionalRepository.findAll();
    }

    public java.util.Optional<Profesional> obtenerPorId(Integer idProfesional) {
        return profesionalRepository.findById(idProfesional);
    }

    public Profesional crear(Profesional profesional) {
        return profesionalRepository.save(profesional);
    }

    public Profesional actualizar(Integer idProfesional, Profesional profesional) {
        Profesional existente = validarExiste(idProfesional);
        existente.setCedula(profesional.getCedula());
        existente.setNombres(profesional.getNombres());
        existente.setApellidos(profesional.getApellidos());
        existente.setActivo(profesional.getActivo());
        return profesionalRepository.save(existente);
    }

    public void eliminar(Integer idProfesional) {
        profesionalRepository.deleteById(idProfesional);
    }
}
