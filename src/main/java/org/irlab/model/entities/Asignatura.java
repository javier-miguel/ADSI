package org.irlab.model.entities;

import jakarta.persistence.*;


import java.time.LocalTime;
@Entity
public class Asignatura{
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

    @Column(name = "hora_fin", nullable = false)
    private LocalTime horaFin;


    @ManyToOne
    @JoinColumn(name = "num_clase")
    private Clase clase;

    @ManyToOne
    @JoinColumn(name = "num_profesor", insertable = false, updatable = false, nullable = false)
    private Profesor profesor;
    public Asignatura(){

    }

    public Asignatura(Long id, String nombre, Integer numAula, Integer diaSemana,
                      LocalTime horaInicio, LocalTime horaFin, Clase clase, Profesor profesor) {
        this.id = id;
        this.nombre = nombre;
        this.numAula = numAula;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.clase = clase;
        this.profesor = profesor;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre(){ return nombre;}

    public void setNombre(String nombre){ this.nombre = nombre;}

    public Integer getNumAula(){ return numAula;}

    public void setNumAula(Integer numAula){ this.numAula = numAula;}

    public Integer getDiaSemanaa(){ return diaSemana;}

    public LocalTime getHoraInicio(){return horaInicio;}

    public void setHoraInicio(LocalTime horaInicio){this.horaInicio = horaInicio;}

    public LocalTime getHoraFin(){return horaFin;}

    public void setHoraFin(LocalTime horaFin){this.horaFin = horaFin;}

    public Clase getClase(){ return clase;}

    public void setClase(Clase clase){ this.clase=clase;}

    public Profesor getProfesor() {return profesor;}

    public void setProfesor(Profesor profesor) {this.profesor = profesor;
    }
}
