package com.fabrica.arquisoft.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fabrica.arquisoft.demo.models.ormModels.DisponibilidadFranjaHoraria;

@Repository
public interface DisponibilidadRepository extends JpaRepository<DisponibilidadFranjaHoraria, Integer> {

    @Override
    @EntityGraph(attributePaths = {
        "profesional",
        "especialidad",
        "consultorio",
        "franjaHoraria"
    }
    )
    List<DisponibilidadFranjaHoraria> findAll();

    // 🔹 Buscar por profesional (cargando relaciones)
    @EntityGraph(attributePaths = {"profesional", "especialidad", "consultorio", "franjaHoraria"})
    List<DisponibilidadFranjaHoraria> findByProfesional_IdProfesional(Integer idProfesional);

    // 🔹 Buscar por especialidad
    @EntityGraph(attributePaths = {"profesional", "especialidad", "consultorio", "franjaHoraria"})
    List<DisponibilidadFranjaHoraria> findByEspecialidad_IdEspecialidad(Integer idEspecialidad);

    // 🔹 Buscar solo las activas
    @EntityGraph(attributePaths = {"profesional", "especialidad", "consultorio", "franjaHoraria"})
    List<DisponibilidadFranjaHoraria> findByActivaTrue();

    // 🔹 Buscar por profesional y activas
    @EntityGraph(attributePaths = {"profesional", "especialidad", "consultorio", "franjaHoraria"})
    List<DisponibilidadFranjaHoraria> findByProfesional_IdProfesionalAndActivaTrue(Integer idProfesional);

}
