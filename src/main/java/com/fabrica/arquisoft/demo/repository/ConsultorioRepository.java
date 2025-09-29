package com.fabrica.arquisoft.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fabrica.arquisoft.demo.models.ormModels.Consultorio;

@Repository
public interface ConsultorioRepository extends JpaRepository<Consultorio, Integer> {}
