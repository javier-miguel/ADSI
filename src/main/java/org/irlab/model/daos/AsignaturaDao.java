package org.irlab.model.daos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.irlab.model.entities.Asignatura;

import java.util.List;

public class AsignaturaDao {
    public static Asignatura findById(EntityManager em, Long id){
        TypedQuery<Asignatura> a = em.createQuery("select a from Asignatura a where a.id = :id", Asignatura.class)
                .setParameter("id", id);

        if(a.getSingleResult() == null){
            return null;
        }else{
            return a.getSingleResult();
        }
    }

    public static List<Asignatura> findByNombre(EntityManager em, String nombre){
        TypedQuery<Asignatura> a = em.createQuery("select a from Asignatura a where a.nombre = :nombre",
                Asignatura.class).setParameter("nombre", nombre);

        return a.getResultList();
    }

    public static List<Asignatura> findByClase(EntityManager em, Long id){
        TypedQuery<Asignatura> a = em.createQuery("select a from Asignatura a where a.clase.id = :num_clase",
                Asignatura.class).setParameter("num_clase", id);

        return a.getResultList();
    }

    public static List<Asignatura> findByProfesor(EntityManager em, String dni){
        TypedQuery<Asignatura> a = em.createQuery("select a from Asignatura a where a.profesor.dni = :num_profesor",
                Asignatura.class).setParameter("num_profesor", dni);

        return a.getResultList();
    }

    public static void update(EntityManager em, Asignatura asignatura){
        em.persist(asignatura);
    }

}
