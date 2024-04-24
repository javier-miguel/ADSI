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

package org.irlab;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;


import javax.annotation.Nonnull;

import org.irlab.common.AppEntityManagerFactory;
import org.irlab.model.exceptions.RoleNotFoundException;
import org.irlab.model.exceptions.UserAlreadyExistsException;
import org.irlab.model.exceptions.UserNotFoundException;
import org.irlab.model.exceptions.ClaseNotFoundException;
import org.irlab.model.exceptions.AlumnoNotFoundException;
import org.irlab.model.exceptions.AsignaturasNotFoundException;
import org.irlab.model.exceptions.ProfesorNotFoundException;
import org.irlab.model.exceptions.AlumnoAlreadyExistsException;
import org.irlab.model.services.RoleService;
import org.irlab.model.services.RoleServiceImpl;
import org.irlab.model.services.UserService;
import org.irlab.model.services.UserServiceImpl;
import org.irlab.model.entities.Asignatura;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder.In;

public class App {

    private enum Command {
        GREET_USER, CHANGE_GREETING, CREATE_USER, CREATE_ALUMNO, UPDATE_ALUMNO, REMOVE_ALUMNO, UPDATE_PROFESOR, SHOW_HORARIO, EXIT
    }

    private static final int CORRECT_SHUTDOWN = 50000;

    private static UserService userService = null;

    private static RoleService roleService = null;

    private static Scanner scanner = null;

    private static void init() {
        try (EntityManager em = AppEntityManagerFactory.getInstance().createEntityManager()) {}
        try {
            userService = new UserServiceImpl();
        } catch (RoleNotFoundException e) {
            System.out.println(
                    """
                            Could not find the default role in the database.

                            This usually means the database has not been populated. The schema has now been created if
                            it didn't exists already. You can now populate the database with:

                              mvn sql:execute

                            Have a good day!
                            """);
            System.exit(1);
        }
        roleService = new RoleServiceImpl();
    }

    private static void shutdown() throws SQLException {
        AppEntityManagerFactory.close();

        try {
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        } catch (SQLException e) {
            if (e.getErrorCode() != CORRECT_SHUTDOWN) {
                throw e;
            }
        }
    }

    private static Command getCommand() {
        System.out.println("Choose an option:");
        System.out.println("  1) Greet user");
        System.out.println("  2) Change user greeting");
        System.out.println("  3) Add a new user");
        System.out.println("  4) Add a new Alumno");
        System.out.println("  5) Update an Alumno");
        System.out.println("  6) Remove an Alumno");
        System.out.println("  7) Update a Profesor");
        System.out.println("  8) Show horario");



        System.out.println();
        System.out.println("  q) Exit");
        System.out.println();
        while (true) {
            System.out.print("Option: ");
            String input = scanner.nextLine();
            if (input.length() == 0) {
                System.out.println("An option needs to be introduced");
            } else if (input.length() > 1) {
                System.err.println(input + " is not a valid option");
            } else {
                switch (input.charAt(0)) {
                case '1':
                    return Command.GREET_USER;
                case '2':
                    return Command.CHANGE_GREETING;
                case '3':
                    return Command.CREATE_USER;
                case '4':
                    return Command.CREATE_ALUMNO;
                case '5':
                    return Command.UPDATE_ALUMNO;
                case '6':
                    return Command.REMOVE_ALUMNO;
                case '7':
                    return Command.UPDATE_PROFESOR;
                case '8':
                    return Command.SHOW_HORARIO;
                case 'q':
                    return Command.EXIT;
                default:
                    System.out.println(input + " is not a valid option");
                }
            }
        }
    }

    private static @Nonnull String readInput(String message, String errorMessage) {
        String result = null;
        while (result == null) {
            System.out.print(message);
            String input = scanner.nextLine();
            if (input.length() == 0) {
                System.out.println(errorMessage);
            } else {
                result = input;
            }
        }
        return result;
    }

    private static void greetUser() {
        String userName = readInput("User name: ", "You must supply a user name");
        String greetingMessage = userService.greet(userName);
        System.out.println(greetingMessage);
    }

    private static void changeGreeting() {
        String userName = readInput("User name: ", "You must supply a user name");
        String newGreeting = readInput("Greeting message: ",
                "You must supply a new greeting message");
        try {
            userService.setUserGreeting(userName, newGreeting);
            System.out.println("User greeting changed");
        } catch (UserNotFoundException e) {
            System.out.println(
                    String.format("Greeting could not be changed, due to the following error:\n%s",
                            e.getMessage()));
        }

    }

    private static @Nonnull String askForRole() {
        String roleList = roleService.getAvailableRoleNames().stream()
                .map(name -> "  - " + name + "\n").collect(Collectors.joining(""));
        String prompt = "Select a role for the user. Avalable roles are:\n" + roleList
                + "User role: ";
        return readInput(prompt, "You must supply a role name");
    }

    private static void createUser() {
        String userName = readInput("User name: ", "You must supply a user name");
        String roleName = askForRole();
        try {
            userService.createUser(userName, roleName);
            System.out.println("User created");
        } catch (UserAlreadyExistsException e) {
            System.out.println("User not created: a user with that name already exists.");
        } catch (RoleNotFoundException e) {
            System.out.println("User not created: invalid role");
        }
    }
    private static void createAlumno() {
        String AlDni = readInput("Alumno's DNI: ", "You must supply an alumno's DNI");
        String AlName = readInput("Alumno's name: ", "You must supply an alumno's name");
        String Al1Ap = readInput("Alumno's first surname: ", "You must supply an alumno's first surname");
        String Al2Ap = readInput("Alumno's second surname: ", "You must supply an alumno's second surname");
        Long AlYear = Long.parseLong(readInput("Alumno's year: ", "You must supply an alumno's year"));
        String AlGroup = readInput("Alumno's group: ", "You must supply an alumno's group");
        try {
            
            userService.createAlumno(AlDni,AlName, Al1Ap, Al2Ap, AlYear, AlGroup);
            System.out.println("Alumno created");
        } catch (AlumnoAlreadyExistsException e) {
            System.out.println("Alumno not created: an Alumno with that DNI already exists.");
        } catch (ClaseNotFoundException e) {
            System.out.println("Alumno not created: invalid class");
        }
    }
    private static void updateAlumno() {
        String AlDni = readInput("Alumno's DNI: ", "You must supply an alumno's DNI");
        Long AlYear = Long.parseLong(readInput("Alumno's year: ", "You must supply an alumno's year"));
        String AlGroup = readInput("Alumno's group: ", "You must supply an alumno's group");
        try {
            
            userService.updateAlumno(AlDni, AlYear, AlGroup);
            System.out.println("Alumno updated");
        } catch (AlumnoNotFoundException e) {
            System.out.println("Alumno not updated: there isn't an Alumno wiith that DNI.");
        } catch (ClaseNotFoundException e) {
            System.out.println("Alumno not updated: invalid class");
        }
    }
    private static void removeAlumno() {
        String AlDni = readInput("Alumno's DNI: ", "You must supply an alumno's DNI");
        try {
            
            userService.removeAlumno(AlDni);
            System.out.println("Alumno removed");
        } catch (AlumnoNotFoundException e) {
            System.out.println("Alumno not removed: There isn't an alumno with that DNI.");
        } 
    }
    private static void updateProfesor() {
        String[] diasSemana = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"};
        List <Asignatura> list = userService.showAsignaturas();
        List <Asignatura> lista = new ArrayList<>();
        Integer i = 0;
        while (list.listIterator().hasNext()){
         Asignatura a = list.get(0);
        System.out.println(i+1 + ": " + diasSemana[a.getDiaSemanaa()-1] + " from "+ a.getHoraInicio() + " to " + a.getHoraFin() + ": " + a.getNombre() + " taught by " + a.getProfesor().getDni());
        i++;
        lista.add(a);
        list.remove(0);
    }   
        String asig = readInput("Choose an Asignatura: ", "You must supply an user's DNI");
        int x=Integer.parseInt(asig);
        String AlDni = readInput("Alumno's DNI: ", "You must supply an profesor's DNI");
        try {
            userService.updateProfesor(lista.get(x-1), AlDni);
            System.out.println("Profesor updated");
        } catch (ProfesorNotFoundException e) {
            System.out.println("Profesor not updated: there isn't a Profesor with that DNI.");
        }
    }

    private static void showHorario(){
        String[] diasSemana = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"};
        String AlDni = readInput("User's DNI: ", "You must supply an user's DNI");
        try {
            List<Asignatura> list = userService.showHorario(AlDni);
            System.out.println("Classes that you have: \n");
            Integer d_actual = 0;
        while (list.listIterator().hasNext()){
            Asignatura a = list.get(0);
            Integer dia = a.getDiaSemanaa();
            if (d_actual != dia){
                System.out.println("\n");
                d_actual=dia;
                System.out.println(diasSemana[d_actual-1]+ "\n");
            }
            System.out.println("-from "+ a.getHoraInicio() + " to " + a.getHoraFin() + ": " + a.getNombre());
            list.remove(0);
        }
        
        } catch (AsignaturasNotFoundException e) {
            System.out.println("Profesor not updated: there isn't a Profesor wiith that DNI.");
        } 
        catch (AlumnoNotFoundException e){

        }
    }

    public static void main(String[] args) throws SQLException {
        init();
        boolean exit = false;
        scanner = new Scanner(System.in);
        while (!exit) {
            System.out.println();
            Command command = getCommand();
            switch (command) {
     
                case GREET_USER -> greetUser();
            case CHANGE_GREETING -> changeGreeting();
            case CREATE_USER -> createUser();
            case CREATE_ALUMNO -> createAlumno();
            case UPDATE_ALUMNO -> updateAlumno();
            case REMOVE_ALUMNO -> removeAlumno();
            case UPDATE_PROFESOR ->updateProfesor();
            case SHOW_HORARIO -> showHorario();
            case EXIT -> exit = true;
            }
        }

        shutdown();
    }
}
