/**
 * Copyright 2022-2024 Information Retrieval Lab
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

import javax.annotation.Nonnull;
import java.util.List;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.irlab.model.entities.Clase;
import org.irlab.model.entities.Asignatura;
import org.irlab.model.exceptions.AlumnoAlreadyExistsException;
import org.irlab.model.exceptions.RoleNotFoundException;
import org.irlab.model.exceptions.UserAlreadyExistsException;
import org.irlab.model.exceptions.UserNotFoundException;
import org.irlab.model.exceptions.AlumnoNotFoundException;
import org.irlab.model.exceptions.AsignaturasNotFoundException;
import org.irlab.model.exceptions.ProfesorNotFoundException;
import org.irlab.model.exceptions.ClaseNotFoundException;

/**
 * The user service facade
 */
public interface UserService {

    /**
     * Get a string greeting the user
     *
     * @param name the name of the user
     * @return the greeting message
     */
    String greet(@Nonnull String name);

    /**
     * Change the user greeting message
     *
     * @param name     the name of the user
     * @param greeting the greeting message
     */
    void setUserGreeting(@Nonnull String name, @Nonnull String greeting)
            throws UserNotFoundException;

    /**
     * Create user with default role and greeting
     *
     * @param name the name of the user
     * @throws UserAlreadyExistsException if a user with the same name already
     *                                    exists
     */
    void createUser(@Nonnull String name) throws UserAlreadyExistsException, RoleNotFoundException;

    /**
     * Create user with the given role and the default greeting message
     *
     * @param name the name of the user
     * @param role the role of the user
     * @throws UserAlreadyExistsException if a user with the same name already
     *                                    exists
     * @throws RoleNotFoundException      if a role with the given name cannot be
     *                                    foundd
     */
    void createAlumno(@Nonnull String DNI, @NonNull String name, @Nonnull String apel1, @Nonnull String apel2, long curso, @Nonnull String grupo) 
                throws AlumnoAlreadyExistsException, ClaseNotFoundException;
    void updateAlumno(@Nonnull String DNI, long curso, @Nonnull String grupo) 
                throws AlumnoNotFoundException, ClaseNotFoundException;

    void removeAlumno(@Nonnull String DNI) 
                throws AlumnoNotFoundException;
     void updateProfesor(Asignatura a, @Nonnull String DNI) 
                throws ProfesorNotFoundException;
     void createUser(@Nonnull String name, @Nonnull String role)
            throws UserAlreadyExistsException, RoleNotFoundException;
     List<Asignatura> showAsignaturas();

List <Asignatura> showHorario(@Nonnull String Dni) throws AsignaturasNotFoundException, AlumnoNotFoundException;
}
