package com.fabrica.arquisoft.demo.service;

import java.time.LocalDate;
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
                disponibilidad.getConsultorio().getIdConsultorio(),
                disponibilidad.getFranjaHoraria().getIdFranja(),
                disponibilidad.getEspecialidad().getIdEspecialidad(),
                disponibilidad.getProfesional().getIdProfesional(), // id del profesional
                disponibilidad.getProfesional().getNombres(), // nombre del profesional
                disponibilidad.getEspecialidad().getEspecialidad(), // nombre de la especialidad
                disponibilidad.getFranjaHoraria().getHoraInicio().toString(), // pasamos LocalTime → String
                disponibilidad.getConsultorio().getNumeroConsultorio(), // número de consultorio
                disponibilidad.getActiva(),
                disponibilidad.getFecha()
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

    // 1️⃣ Validaciones de reglas de negocio
    if (dto.fecha().isBefore(LocalDate.now())) {
        throw new IllegalArgumentException("La fecha no puede estar en el pasado");
    }

    boolean existe = disponibilidadRepository
        .existsByProfesionalIdProfesionalAndFechaAndFranjaHorariaIdFranja(
            dto.idProfesional(),
            dto.fecha(),
            dto.idFranjaHoraria()
        );

    if (existe) {
        throw new IllegalStateException(
            "El profesional ya tiene una disponibilidad asignada para esa fecha y franja horaria"
        );
    }

    // 2️⃣ Validar entidades externas
    Profesional profesional = profesionalService.validarExiste(dto.idProfesional());
    Especialidad especialidad = especialidadService.validarExiste(dto.idEspecialidad());
    FranjaHoraria franja = franjaHorariaService.validarExiste(dto.idFranjaHoraria());
    Consultorio consultorio = consultorioService.validarExiste(dto.idConsultorio());

    // 3️⃣ Mapear DTO → Entidad
    DisponibilidadFranjaHoraria disponibilidad = mapToEntity(dto);
    disponibilidad.setProfesional(profesional);
    disponibilidad.setEspecialidad(especialidad);
    disponibilidad.setFranjaHoraria(franja);
    disponibilidad.setConsultorio(consultorio);
    disponibilidad.setFecha(dto.fecha());

    // 4️⃣ Guardar
    DisponibilidadFranjaHoraria guardada = disponibilidadRepository.save(disponibilidad);

    return mapToDTO(guardada);
}


    public Optional<DisponibilidadResponseDTO> actualizar(Integer id, DisponibilidadRequestDto dto) {
        return disponibilidadRepository.findById(id).map(actual -> {
            actual.setActiva(dto.activa());
            actual.setFecha(dto.fecha());
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

    // 🔹 Inactivar disponibilidades por bloqueo de profesional
    public void inactivarDisponibilidadesPorBloqueo(Integer idProfesional, LocalDate fechaInicio, LocalDate fechaFin) {
        List<DisponibilidadFranjaHoraria> disponibilidades = disponibilidadRepository
            .findByProfesional_IdProfesionalAndFechaBetween(idProfesional, fechaInicio, fechaFin);
        
        disponibilidades.forEach(disp -> disp.setActiva(false));
        disponibilidadRepository.saveAll(disponibilidades);
    }

}
