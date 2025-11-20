package com.fabrica.arquisoft.demo.models.dtos;
import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public record DisponibilidadRequestDto(

    @NotNull
    Integer idProfesional,

    @NotNull
    Integer idEspecialidad,

    @NotNull
    Short idFranjaHoraria,

    @NotNull
    Integer idConsultorio,

    @NotNull
    Boolean activa,
    
    @NotNull(message = "La fecha es obligatoria")
    LocalDate fecha
){}
