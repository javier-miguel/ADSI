package org.irlab.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
public class Clase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "curso", nullable = false)
    private Long curso;

    @Column(name = "grupo", nullable = false)
    private Long grupo;

    public Clase(){

    }

    public Clase(Long id, Long curso, Long grupo) {
        this.id = id;
        this.curso = curso;
        this.grupo = grupo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCurso() {
        return curso;
    }

    public void setCurso(Long curso) {
        this.curso = curso;
    }

    public Long getGrupo() {
        return grupo;
    }

    public void setGrupo(Long grupo) {
        this.grupo = grupo;
    }
}
