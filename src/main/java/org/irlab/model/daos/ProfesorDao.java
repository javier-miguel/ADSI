package org.irlab.model.daos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.irlab.model.entities.Profesor;

import java.util.List;

public class ProfesorDao {

    public static Profesor findByDni(EntityManager em, String dni){
        TypedQuery<Profesor> p = em.createQuery("select p from Profesor p where p.dni = :dni", Profesor.class)
                .setParameter("dni", dni);

        if(p.getSingleResult() == null){
            return null;
        }else{
            return p.getSingleResult();
        }
    }

    public static List<Profesor> findByNombre(EntityManager em, String nombre){
        TypedQuery<Profesor> p = em.createQuery("select p from Profesor p where p.nombre = :nombre",
                        Profesor.class).setParameter("nombre", nombre);

        return p.getResultList();
    }

    public static void update(EntityManager em, Profesor profesor){
        em.persist(profesor);
    }
}
