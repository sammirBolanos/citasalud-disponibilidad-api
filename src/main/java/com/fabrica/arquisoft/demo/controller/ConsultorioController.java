package com.fabrica.arquisoft.demo.controller;

import com.fabrica.arquisoft.demo.models.ormModels.Consultorio;
import com.fabrica.arquisoft.demo.service.ConsultorioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/consultorios")
public class ConsultorioController {

    private final ConsultorioService service;

    public ConsultorioController(ConsultorioService service) {
        this.service = service;
    }


    @GetMapping
    public List<Consultorio> obtenerTodos() {
        return service.obtenerTodos();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Consultorio> obtenerPorId(@PathVariable Integer id) {
        return service.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    public Consultorio crear(@RequestBody Consultorio consultorio) {
        return service.crear(consultorio);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Consultorio> actualizar(@PathVariable Integer id, @RequestBody Consultorio consultorio) {
        try {
            return ResponseEntity.ok(service.actualizar(id, consultorio));
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
