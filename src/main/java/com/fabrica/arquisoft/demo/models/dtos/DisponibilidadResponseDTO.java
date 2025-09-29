package com.fabrica.arquisoft.demo.models.dtos;

public record DisponibilidadResponseDTO(
    Integer idDisponibilidad,
    Integer idProfesional,
    String nombreProfesional,
    String nombreEspecialidad,
    String horaFranja,
    Integer numeroConsultorio,
    Boolean activa
) {}
