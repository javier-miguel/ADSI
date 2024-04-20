package org.irlab.model.entities;

import jakarta.persistence.*;
@Entity
public class Alumno {
    @Id
    @Column(name = "DNI", unique = true, nullable = false)
    private String dni;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "apellido1", nullable = false)
    private String apellido1;

    @Column(name = "apellido2", nullable = false)
    private String apellido2;

    @ManyToOne
    @JoinColumn(name = "num_clase")
    private Clase clase;

    public Alumno(){

    }

    public Alumno(String dni, String nombre, String apellido1, String apellido2, Clase clase) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.clase = clase;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public Clase getClase(){ return clase;}

    public void setClase(Clase clase){ this.clase = clase;}

}
