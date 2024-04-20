package org.irlab.model.daos;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.TypedQuery;
import org.irlab.model.entities.Clase;
import org.irlab.model.entities.Profesor;
import org.irlab.model.entities.User;

import java.util.List;
import java.util.Optional;

public class ClaseDao {

     public static Clase findById(EntityManager em, Long id){
         TypedQuery<Clase> c = em.createQuery("select c from Clase c where c.id = :id", Clase.class)
                 .setParameter("id", id);

         if(c.getSingleResult() == null){
            return null;
         }else{
             return c.getSingleResult();
         }
     }

     public static List<Clase> findByCurso(EntityManager em, Long curso){
         TypedQuery<Clase> c = em.createQuery("select c from Clase c where c.curso = :curso", Clase.class)
                 .setParameter("curso", curso);

         return c.getResultList();
     }

     public static void update(EntityManager em, Clase clase){
         em.persist(clase);
     }
}
