package com.fabrica.arquisoft.demo.models.dtos;

public record DisponibilidadRequestDto(
    Integer idProfesional,
    Integer idEspecialidad,
    Short idFranjaHoraria,
    Integer idConsultorio,
    boolean activa
){}
