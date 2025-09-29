package com.fabrica.arquisoft.demo.models.ormModels;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tbl_consultorios")
public class Consultorio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_consultorio", nullable = false)
    private Integer idConsultorio;

    @Column(name = "numero_consultorio")
    private Integer numeroConsultorio;

    @Size(max = 255)
    @Column(name = "tipo_consultorio")
    private String tipoConsultorio;

    public Integer getIdConsultorio() {
        return idConsultorio;
    }

    public void setIdConsultorio(Integer idConsultorio) {
        this.idConsultorio = idConsultorio;
    }

    public Integer getNumeroConsultorio() {
        return numeroConsultorio;
    }

    public void setNumeroConsultorio(Integer numeroConsultorio) {
        this.numeroConsultorio = numeroConsultorio;
    }

    public String getTipoConsultorio() {
        return tipoConsultorio;
    }

    public void setTipoConsultorio(String tipoConsultorio) {
        this.tipoConsultorio = tipoConsultorio;
    }


}
