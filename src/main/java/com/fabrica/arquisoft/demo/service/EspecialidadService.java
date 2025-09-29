package com.fabrica.arquisoft.demo.service;

import java.util.List;
import java.util.Optional;

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

    // Validar si existe
    public Especialidad validarExiste(Integer idEspecialidad) {
        return especialidadRepository.findById(idEspecialidad)
                .orElseThrow(() -> new EntityNotFoundException("Especialidad con id " + idEspecialidad + " no existe"));
    }

    // Obtener todas
    public List<Especialidad> obtenerTodas() {
        return especialidadRepository.findAll();
    }

    // Obtener por ID
    public Optional<Especialidad> obtenerPorId(Integer id) {
        return especialidadRepository.findById(id);
    }

    // Crear
    public Especialidad crear(Especialidad especialidad) {
        return especialidadRepository.save(especialidad);
    }

    // Actualizar
    public Especialidad actualizar(Integer idEspecialidad, Especialidad especialidad) {
        Especialidad existente = validarExiste(idEspecialidad);
        existente.setEspecialidad(especialidad.getEspecialidad());
        return especialidadRepository.save(existente);
    }

    // Eliminar
    public void eliminar(Integer id) {
        Especialidad existente = validarExiste(id);
        especialidadRepository.delete(existente);
    }
}
