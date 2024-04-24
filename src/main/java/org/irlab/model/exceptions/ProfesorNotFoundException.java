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

package org.irlab.model.exceptions;
import org.irlab.model.entities.Profesor;


public class ProfesorNotFoundException extends Exception {
    private final Profesor userName;

    public ProfesorNotFoundException(Profesor userName) {
        super(String.format("Profesor %s doesn't exist", userName));
        this.userName = userName;
    }

    public Profesor getUserName() {
        return userName;
    }
}
