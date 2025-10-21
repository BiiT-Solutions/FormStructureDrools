package com.biit.drools.variables;

/*-
 * #%L
 * Form Structure Drools Engine
 * %%
 * Copyright (C) 2015 - 2025 BiiT Sourcing Solutions S.L.
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * #L%
 */

import com.biit.drools.log.DroolsSubmittedLogger;

/**
 * Class for storing ABCD Custom Variables values.
 */
public class FormVariableValue {

    //TreeObject name or comparationId;
    private String reference;

    //Variable Name
    private String variable;

    //Value of the variable
    private Object value;

    public FormVariableValue(String reference, String variable, Object value) {
        this.reference = reference;
        this.variable = variable;
        this.value = value;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        DroolsSubmittedLogger.debug(this.getClass().getName(), "Changing form variable '{}' from '{}' to '{}'.", variable, this.value, value);
        this.value = value;
    }
}
