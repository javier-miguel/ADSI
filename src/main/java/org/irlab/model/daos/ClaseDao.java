package org.irlab.model.daos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.irlab.model.entities.Clase;


import java.util.List;

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
     public static Clase findByCursoYClase(EntityManager em, Long curso, String grupo){
        TypedQuery<Clase> c = em.createQuery("select c from Clase c where c.curso = :curso and c.grupo = :grupo", Clase.class).setParameter("curso",curso).setParameter("grupo", grupo);

        if(c.getSingleResult() == null){
            return null;
        }else{
            return c.getSingleResult();
        }
    }
}
