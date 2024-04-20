package org.irlab.model.entities;

import jakarta.persistence.*;

@Entity
public class Clase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "curso", nullable = false)
    private Long curso;

    @Column(name = "grupo", nullable = false)
    private String grupo;

    public Clase(){

    }

    public Clase(Long id, Long curso, String grupo) {
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

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }
}
