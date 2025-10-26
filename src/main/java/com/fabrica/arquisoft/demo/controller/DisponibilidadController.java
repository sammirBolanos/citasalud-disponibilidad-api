package com.fabrica.arquisoft.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fabrica.arquisoft.demo.models.dtos.DisponibilidadRequestDto;
import com.fabrica.arquisoft.demo.models.dtos.DisponibilidadResponseDTO;
import com.fabrica.arquisoft.demo.service.DisponibilidadService;

@RestController
public class DisponibilidadController {

    private final DisponibilidadService disponibilidadService;

    
    public DisponibilidadController(DisponibilidadService disponibilidadService) {
        this.disponibilidadService = disponibilidadService;
    }

    //Tabla completa
    @GetMapping
    public List<DisponibilidadResponseDTO> obtenerTodas() {
        return disponibilidadService.obtenerTodas();
    }

    // Por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer id) {
        return disponibilidadService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Por profesional
    @GetMapping("/profesional/{idProfesional}")
    public ResponseEntity<List<DisponibilidadResponseDTO>> obtenerPorProfesional(@PathVariable Integer idProfesional) {
        return ResponseEntity.ok(disponibilidadService.obtenerPorProfesional(idProfesional));
    }

    // Por especialidad
    @GetMapping("/especialidad/{idEspecialidad}")
    public ResponseEntity<List<DisponibilidadResponseDTO>> obtenerPorEspecialidad(@PathVariable Integer idEspecialidad) {
        return ResponseEntity.ok(disponibilidadService.obtenerPorEspecialidad(idEspecialidad));
    }

    // Solo activas
    @GetMapping("/activas")
    public ResponseEntity<List<DisponibilidadResponseDTO>> obtenerActivas() {
        return ResponseEntity.ok(disponibilidadService.obtenerActivas());
    }

    // Activas por profesional
    @GetMapping("/profesional/{idProfesional}/activas")
    public ResponseEntity<List<DisponibilidadResponseDTO>> obtenerActivasPorProfesional(@PathVariable Integer idProfesional) {
        return ResponseEntity.ok(disponibilidadService.obtenerActivasPorProfesional(idProfesional));
    }

    //Crear nueva disponibilidad
    @PostMapping("/crear")
    public ResponseEntity<?> crear(@RequestBody DisponibilidadRequestDto dto) {
    
        DisponibilidadResponseDTO nueva = disponibilidadService.crearConValidacion(dto);
        return ResponseEntity.ok(nueva);
        
    }

    //Actualizar disponibilidad existente
    @PutMapping("/{id}")
public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody DisponibilidadRequestDto dto) {
    return disponibilidadService.actualizar(id, dto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
}

    //Eliminar disponibilidad por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        return disponibilidadService.obtenerPorId(id)
                .map(d -> {
                    disponibilidadService.eliminar(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}

