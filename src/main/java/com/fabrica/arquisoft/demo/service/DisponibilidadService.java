package com.fabrica.arquisoft.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fabrica.arquisoft.demo.models.dtos.DisponibilidadRequestDto;
import com.fabrica.arquisoft.demo.models.dtos.DisponibilidadResponseDTO;
import com.fabrica.arquisoft.demo.models.ormModels.Consultorio;
import com.fabrica.arquisoft.demo.models.ormModels.DisponibilidadFranjaHoraria;
import com.fabrica.arquisoft.demo.models.ormModels.Especialidad;
import com.fabrica.arquisoft.demo.models.ormModels.FranjaHoraria;
import com.fabrica.arquisoft.demo.models.ormModels.Profesional;
import com.fabrica.arquisoft.demo.repository.DisponibilidadRepository;

@Service
public class DisponibilidadService {

    private final DisponibilidadRepository disponibilidadRepository;
    private final ProfesionalService profesionalService;
    private final EspecialidadService especialidadService;
    private final FranjaHorariaService franjaHorariaService;
    private final ConsultorioService consultorioService;

    public DisponibilidadService(
            DisponibilidadRepository disponibilidadRepository,
            ProfesionalService profesionalService,
            EspecialidadService especialidadService,
            FranjaHorariaService franjaHorariaService,
            ConsultorioService consultorioService
    ) {
        this.disponibilidadRepository = disponibilidadRepository;
        this.profesionalService = profesionalService;
        this.especialidadService = especialidadService;
        this.franjaHorariaService = franjaHorariaService;
        this.consultorioService = consultorioService;
    }

    private DisponibilidadResponseDTO mapToDTO(DisponibilidadFranjaHoraria disponibilidad) {
        return new DisponibilidadResponseDTO(
                disponibilidad.getIdDisponibilidad(),
                disponibilidad.getProfesional().getIdProfesional(), // id del profesional
                disponibilidad.getProfesional().getNombres(), // nombre del profesional
                disponibilidad.getEspecialidad().getEspecialidad(), // nombre de la especialidad
                disponibilidad.getFranjaHoraria().getHoraInicio().toString(), // pasamos LocalTime → String
                disponibilidad.getConsultorio().getNumeroConsultorio(), // número de consultorio
                disponibilidad.getActiva()
        );
    }

    public List<DisponibilidadResponseDTO> obtenerTodas() {
        return disponibilidadRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public Optional<DisponibilidadResponseDTO> obtenerPorId(Integer idDisponibilidad) {
        return disponibilidadRepository.findById(idDisponibilidad)
                .map(this::mapToDTO);
    }

    public List<DisponibilidadResponseDTO> obtenerPorProfesional(Integer idProfesional) {
        return disponibilidadRepository.findByProfesional_IdProfesional(idProfesional)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public List<DisponibilidadResponseDTO> obtenerPorEspecialidad(Integer idEspecialidad) {
        return disponibilidadRepository.findByEspecialidad_IdEspecialidad(idEspecialidad)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public List<DisponibilidadResponseDTO> obtenerActivas() {
        return disponibilidadRepository.findByActivaTrue()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public List<DisponibilidadResponseDTO> obtenerActivasPorProfesional(Integer idProfesional) {
        return disponibilidadRepository.findByProfesional_IdProfesionalAndActivaTrue(idProfesional)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    private DisponibilidadFranjaHoraria mapToEntity(DisponibilidadRequestDto dto) {
        DisponibilidadFranjaHoraria disponibilidad = new DisponibilidadFranjaHoraria();
        disponibilidad.setActiva(dto.activa());
        return disponibilidad;
    }

    public DisponibilidadResponseDTO crearConValidacion(DisponibilidadRequestDto dto) {
        Profesional profesional = profesionalService.validarExiste(dto.idProfesional());
        Especialidad especialidad = especialidadService.validarExiste(dto.idEspecialidad());
        FranjaHoraria franja = franjaHorariaService.validarExiste(dto.idFranjaHoraria());
        Consultorio consultorio = consultorioService.validarExiste(dto.idConsultorio());

        DisponibilidadFranjaHoraria disponibilidad = mapToEntity(dto);
        disponibilidad.setProfesional(profesional);
        disponibilidad.setEspecialidad(especialidad);
        disponibilidad.setFranjaHoraria(franja);
        disponibilidad.setConsultorio(consultorio);

        DisponibilidadFranjaHoraria guardada = disponibilidadRepository.save(disponibilidad);

        return mapToDTO(guardada); // 👈 devolvemos DTO, no la entidad
    }

    public Optional<DisponibilidadResponseDTO> actualizar(Integer id, DisponibilidadRequestDto dto) {
        return disponibilidadRepository.findById(id).map(actual -> {
            actual.setActiva(dto.activa());
            actual.setProfesional(profesionalService.validarExiste(dto.idProfesional()));
            actual.setEspecialidad(especialidadService.validarExiste(dto.idEspecialidad()));
            actual.setFranjaHoraria(franjaHorariaService.validarExiste(dto.idFranjaHoraria()));
            actual.setConsultorio(consultorioService.validarExiste(dto.idConsultorio()));

            DisponibilidadFranjaHoraria actualizado = disponibilidadRepository.save(actual);
            return mapToDTO(actualizado);
        });
    }

    // 🔹 Eliminar una disponibilidad por ID
    public void eliminar(Integer idDisponibilidad) {
        disponibilidadRepository.deleteById(idDisponibilidad);
    }

}
