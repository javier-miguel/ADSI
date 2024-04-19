package org.irlab.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.sql.Time;
import java.time.LocalTime;

public class Asignatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "num_aula", nullable = false)
    private Integer numAula;

    @Column(name = "dia_semana", nullable = false)
    private Integer diaSemana;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaFin;

    public Asignatura(){

    }

    public Asignatura(Long id, String nombre, Integer numAula, Integer diaSemana,
                      LocalTime horaInicio, LocalTime horaFin) {
        this.id = id;
        this.nombre = nombre;
        this.numAula = numAula;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre(){ return nombre;}

    public void setNombre(String Nombre){ this.nombre = nombre;}

    public Integer getNumAula(){ return numAula;}

    public void setNumAula(Integer numAula){ this.numAula = numAula;}

    public Integer getDiaSemanaa(){ return diaSemana;}

    public void setDiaSemana(Integer diaSemana){ this.diaSemana = diaSemana; }

    public LocalTime getHoraInicio(){return horaInicio;}

    public void setHoraInicio(LocalTime horaInicio){this.horaInicio = horaInicio;}

    public LocalTime getHoraFin(){return horaFin;}

    public void setHoraFin(LocalTime horaFin){this.horaFin = horaFin;}


}
