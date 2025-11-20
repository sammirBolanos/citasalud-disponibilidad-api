package com.fabrica.arquisoft.demo.models.dtos;

import java.time.LocalDate;

public record DisponibilidadResponseDTO(
    Integer idDisponibilidad,
    Integer idConsultorio,
    Short idFranjaHoraria,
    Integer idEspecialidad,
    Integer idProfesional,
    String nombreProfesional,
    String nombreEspecialidad,
    String horaFranja,
    Integer numeroConsultorio,
    Boolean activa,
    LocalDate fecha
) {}