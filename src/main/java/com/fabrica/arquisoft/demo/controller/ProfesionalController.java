package com.fabrica.arquisoft.demo.controller;

import com.fabrica.arquisoft.demo.models.ormModels.Profesional;
import com.fabrica.arquisoft.demo.service.ProfesionalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profesionales")
public class ProfesionalController {

    private final ProfesionalService service;

    public ProfesionalController(ProfesionalService service) {
        this.service = service;
    }

    @GetMapping
    public List<Profesional> obtenerTodos() {
        return service.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profesional> obtenerPorId(@PathVariable Integer id) {
        return service.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Profesional crear(@RequestBody Profesional profesional) {
        return service.crear(profesional);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Profesional> actualizar(@PathVariable Integer id, @RequestBody Profesional profesional) {
        try {
            return ResponseEntity.ok(service.actualizar(id, profesional));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
