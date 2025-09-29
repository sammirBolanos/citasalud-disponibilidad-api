package com.fabrica.arquisoft.demo.controller;

import com.fabrica.arquisoft.demo.models.ormModels.FranjaHoraria;
import com.fabrica.arquisoft.demo.service.FranjaHorariaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/franjas-horarias")
public class FranjaHorariaController {

    private final FranjaHorariaService service;

    public FranjaHorariaController(FranjaHorariaService service) {
        this.service = service;
    }

    @GetMapping
    public List<FranjaHoraria> obtenerTodas() {
        return service.obtenerTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FranjaHoraria> obtenerPorId(@PathVariable Short id) {
        return service.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public FranjaHoraria crear(@RequestBody FranjaHoraria franjaHoraria) {
        return service.crear(franjaHoraria);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FranjaHoraria> actualizar(@PathVariable Short id, @RequestBody FranjaHoraria franjaHoraria) {
        try {
            return ResponseEntity.ok(service.actualizar(id, franjaHoraria));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Short id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
