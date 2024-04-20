package org.irlab.model.daos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.irlab.model.entities.Alumno;

import java.util.List;

public class AlumnoDao {

    public static Alumno findByDni(EntityManager em, String dni){
        TypedQuery<Alumno> a = em.createQuery("select a from Alumno a where a.dni = :dni", Alumno.class)
                .setParameter("dni", dni);

        if(a.getSingleResult() == null){
            return null;
        }else{
            return a.getSingleResult();
        }
    }

    public static List<Alumno> findByNombre(EntityManager em, String nombre){
        TypedQuery<Alumno> a = em.createQuery("select a from Alumno a where a.nombre = :nombre",
                Alumno.class).setParameter("nombre", nombre);

        return a.getResultList();
    }

    public static void update(EntityManager em, Alumno alumno){
        em.persist(alumno);
    }

}
