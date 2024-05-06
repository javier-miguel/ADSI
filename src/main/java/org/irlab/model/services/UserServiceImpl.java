/**
 * Copyright 2022 Information Retrieval Lab
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.irlab.model.services;

import java.util.List;
import java.util.Collections;

import javax.annotation.Nonnull;

import org.irlab.common.AppEntityManagerFactory;
import org.irlab.model.daos.AlumnoDao;
import org.irlab.model.daos.RoleDao;

import org.irlab.model.daos.ProfesorDao;
import org.irlab.model.daos.AsignaturaDao;
import org.irlab.model.daos.ClaseDao;
import org.irlab.model.entities.Role;
import org.irlab.model.entities.Alumno;
import org.irlab.model.entities.Profesor;
import org.irlab.model.entities.Asignatura;
import org.irlab.model.entities.Clase;
import org.irlab.model.exceptions.RoleNotFoundException;
import org.irlab.model.exceptions.AlumnoAlreadyExistsException;
import org.irlab.model.exceptions.AlumnoNotFoundException;
import org.irlab.model.exceptions.AsignaturasNotFoundException;
import org.irlab.model.exceptions.ProfesorNotFoundException;
import org.irlab.model.exceptions.ClaseNotFoundException;

/**
 * Implementation of the user service facade
 */
public class UserServiceImpl implements UserService {

    private static final String DEFAULT_ROLE_NAME = "alumno";


    public UserServiceImpl() throws RoleNotFoundException {
        try (var em = AppEntityManagerFactory.getInstance().createEntityManager()) {
            RoleDao.findByName(em, DEFAULT_ROLE_NAME)
                    .orElseThrow(() -> new RoleNotFoundException(DEFAULT_ROLE_NAME));
        }
    }

    /* Constructor for tests, not a good practice */
    UserServiceImpl(boolean createDefault) {
        try (var em = AppEntityManagerFactory.getInstance().createEntityManager()) {
            var role = new Role(DEFAULT_ROLE_NAME);
            em.persist(role);
        }
    }


   
    @Override
    public void createAlumno (@Nonnull String DNI, @Nonnull String nombre, @Nonnull String apel1, @Nonnull String apel2, long curso, @Nonnull String grupo) throws AlumnoAlreadyExistsException, ClaseNotFoundException{
        try (var em = AppEntityManagerFactory.getInstance().createEntityManager()) {
            Alumno r = AlumnoDao.findByDni(em, DNI);
            if (r != null){
                throw new AlumnoAlreadyExistsException(r);
            }
            Clase p = null;
            try {p = ClaseDao.findByCursoYClase(em, curso, grupo);}
            catch (Exception e){throw new ClaseNotFoundException(p);}
            
                Alumno alumno = new Alumno(DNI, nombre, apel1, apel2, p);
                try {
                    em.getTransaction().begin();
                    em.persist(alumno);
                    em.getTransaction().commit();
                } catch (Exception e) {
                    em.getTransaction().rollback();
                    throw e;
                }
            }
        }
        @Override

        public void updateAlumno(@Nonnull String DNI, long curso, @Nonnull String grupo) throws AlumnoNotFoundException, ClaseNotFoundException
        { 
            try (var em = AppEntityManagerFactory.getInstance().createEntityManager()) {
                Alumno r = AlumnoDao.findByDni(em, DNI);
                if (r == null){
                    throw new AlumnoNotFoundException(r);
                }
                Clase p = null;
                try {p = ClaseDao.findByCursoYClase(em, curso, grupo);}
                catch (Exception e){throw new ClaseNotFoundException(p);}
                    try {
                        em.getTransaction().begin();
                        AlumnoDao.update(em, r);
                        em.getTransaction().commit();
                    } catch (Exception e) {
                        em.getTransaction().rollback();
                        throw e;
                    }
                }



                }
    @Override
    public void removeAlumno(@Nonnull String DNI) throws AlumnoNotFoundException
                { 
                    try (var em = AppEntityManagerFactory.getInstance().createEntityManager()) {
                        Alumno alumno = AlumnoDao.findByDni(em, DNI);
                        if (alumno == null){
                            throw new AlumnoNotFoundException(alumno);
                        }
                        
                            try {
                                em.getTransaction().begin();
                                em.remove(alumno);
                                em.getTransaction().commit();
                            } catch (Exception e) {
                                em.getTransaction().rollback();
                                throw e;
                            }
                        }
        
        
        
                        }
    @Override
    public void updateProfesor(Asignatura a, @Nonnull String DNI) throws ProfesorNotFoundException
        { 
            try (var em = AppEntityManagerFactory.getInstance().createEntityManager()) {
                Profesor r = ProfesorDao.findByDni(em,DNI);
                if (r == null){
                    throw new ProfesorNotFoundException(r);
                }         
                    try {
                        em.getTransaction().begin();
                        Asignatura m = em.merge(a);
                        em.remove(m);
                        //em.getTransaction().commit();
                        m.setProfesor(r);
                        //em.getTransaction().begin();
                        em.persist(m);       
                        em.getTransaction().commit();
                    } catch (Exception e) {
                        em.getTransaction().rollback();
                        throw e;
                    }
                }



                }
        
    public List<Asignatura> showHorario(@Nonnull String Dni) throws AlumnoNotFoundException, AsignaturasNotFoundException{
        try (var em = AppEntityManagerFactory.getInstance().createEntityManager()) {
            boolean alumno = true;
        Alumno a = AlumnoDao.findByDni(em, Dni);
        if (a == null){
            alumno=false;
            Profesor p = ProfesorDao.findByDni(em, Dni);
            if (p ==null){
                throw new AlumnoNotFoundException(a);
            }
        }
        List<Asignatura> list;
        if (alumno){
            list = AsignaturaDao.findByClase(em, a.getClase().getId());
        }
        else{
            list =AsignaturaDao.findByProfesor(em, Dni);
        }
        if (list.isEmpty()){
            throw new AsignaturasNotFoundException(null);
        }
        Collections.sort(list,new AsignaturaComparator());
        return list;
    }
}
public List<Asignatura> showAsignaturas(){
    try (var em = AppEntityManagerFactory.getInstance().createEntityManager()) {
    return AsignaturaDao.findAll(em);
    
    }
}
class AsignaturaComparator implements java.util.Comparator<Asignatura> {
    public int compareDia(Asignatura a, Asignatura b) {
        return a.getDiaSemanaa()-b.getDiaSemanaa();
    }
    public int compareHora (Asignatura a, Asignatura b){
        return a.getHoraInicio().compareTo(b.getHoraInicio());
    }
    public int compare (Asignatura a, Asignatura b){
        int x=compareDia(a, b);
        if(x==0){x=compareHora(a,b);}
        return x;
    }
}
 }


