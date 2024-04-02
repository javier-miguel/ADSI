package org.irlab.model.daos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.irlab.model.entities.Profesor;

import java.util.List;

public class ProfesorDao {

    public static List<Profesor> getProfesors(EntityManager em) {
        TypedQuery<Profesor> q = em.createQuery("select p from Profesor p", Profesor.class);
        return q.getResultList();
    }
}
