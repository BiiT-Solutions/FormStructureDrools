package com.biit.drools.form;

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

import com.biit.drools.form.serialization.DroolsSubmittedSystemFieldDeserializer;
import com.biit.drools.form.serialization.DroolsSubmittedSystemFieldSerializer;
import com.biit.form.submitted.ISubmittedFormElement;
import com.biit.form.submitted.ISubmittedObject;
import com.biit.form.submitted.implementation.SubmittedSystemField;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;
import java.util.Map;

@JsonDeserialize(using = DroolsSubmittedSystemFieldDeserializer.class)
@JsonSerialize(using = DroolsSubmittedSystemFieldSerializer.class)
public class DroolsSubmittedSystemField extends SubmittedSystemField implements ISubmittedFormElement {

    public DroolsSubmittedSystemField(String tag) {
        super(tag);
    }

    public DroolsSubmittedSystemField() {
        super();
    }

    @Override
    public boolean isVariableDefined(String varName) {
        return isVariableDefined(this, varName);
    }

    @Override
    public boolean isVariableDefined(ISubmittedObject submittedFormTreeObject, String varName) {
        return ((ISubmittedFormElement) getParent()).isVariableDefined(submittedFormTreeObject, varName);
    }

    @Override
    public Object getVariableValue(String varName) {
        return getVariableValue(this, varName);
    }

    @Override
    public <T extends ISubmittedObject> Object getVariableValue(Class<T> type, String varName) {
        final List<T> childs = getChildrenRecursive(type);

        if (childs != null && !childs.isEmpty()) {
            return getVariableValue(childs.get(0), varName);
        }
        return null;
    }

    @Override
    public <T extends ISubmittedObject> Object getVariableValue(Class<T> type, String treeObjectName, String varName) {
        final ISubmittedObject child = getChild(type, treeObjectName);

        if (child != null) {
            return getVariableValue(child, varName);
        }
        return null;
    }

    @Override
    public Object getVariableValue(ISubmittedObject submittedObject, String varName) {
        return ((ISubmittedFormElement) this.getParent()).getVariableValue(submittedObject, varName);
    }

    @Override
    public void setVariableValue(String varName, Object value) {
        setVariableValue(this, varName, value);
    }

    @Override
    public void setVariableValue(ISubmittedObject submittedObject, String varName, Object value) {
        ((ISubmittedFormElement) getParent()).setVariableValue(submittedObject, varName, value);
    }

    @Override
    public String generateXML(String tabs) {
        return "";
    }

    @Override
    public String getName() {
        return getTag();
    }

    @Override
    public String getOriginalValue() {
        return getValue();
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public Map<String, Object> getVariablesValue(ISubmittedObject submittedFormObject) {
        return ((ISubmittedFormElement) this.getParent()).getVariablesValue(submittedFormObject);
    }

    @Override
    public Map<String, Object> getVariablesValue() {
        return getVariablesValue(this);
    }
}
