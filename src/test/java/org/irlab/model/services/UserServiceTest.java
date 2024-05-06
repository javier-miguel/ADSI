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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.DriverManager;
import java.sql.SQLException;

import org.irlab.common.AppEntityManagerFactory;
import org.irlab.model.entities.Role;
import org.irlab.model.entities.User;
import org.irlab.model.exceptions.UserNotFoundException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManager;

public class UserServiceTest {

    private static final UserService userService = new UserServiceImpl(true);

    private static final String KNOWN_USER = "KnownUser";
    private static final String KNOWN_USER_GREETING = "Yo";
    private static final String KNOWN_ROLE_NAME = "roleName";

    @BeforeAll
    static void initTestData() {
        AppEntityManagerFactory.configurePersistenceUnit("test-persistence-unit");
        Role knownRole = new Role(KNOWN_ROLE_NAME);

        EntityManager em = AppEntityManagerFactory.getInstance().createEntityManager();
        em.getTransaction().begin();
        em.persist(knownRole);
        em.getTransaction().commit();

        User knownUser = new User(KNOWN_USER, KNOWN_USER_GREETING, knownRole);
        em.getTransaction().begin();
        em.persist(knownUser);
        em.getTransaction().commit();

        em.close();
    }

    @AfterAll
    static void shutdown() throws SQLException {
        AppEntityManagerFactory.close();
        AppEntityManagerFactory.resetPersistenceUnit();

        try {
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        } catch (SQLException e) {
            if (e.getErrorCode() != 50000) {
                throw e;
            }
        }
    }

    

}
