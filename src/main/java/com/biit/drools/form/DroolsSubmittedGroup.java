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

import com.biit.drools.log.DroolsSubmittedLogger;
import com.biit.form.submitted.ISubmittedFormElement;
import com.biit.form.submitted.ISubmittedObject;
import com.biit.form.submitted.implementation.SubmittedGroup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class DroolsSubmittedGroup extends SubmittedGroup implements ISubmittedFormElement {

    public DroolsSubmittedGroup(String tag) {
        super(tag);
    }

    @Override
    public boolean isVariableDefined(String varName) {
        return isVariableDefined(this, varName);
    }

    @Override
    public boolean isVariableDefined(ISubmittedObject submittedFormTreeObject, String varName) {
        return ((ISubmittedFormElement) getParent()).isVariableDefined(submittedFormTreeObject, varName);
    }

    public boolean isScoreNotSet(String varName) {
        return !isVariableDefined(varName);
    }

    @Override
    public Object getVariableValue(String varName) {
        return getVariableValue(this, varName);
    }

    @Override
    public Map<String, Object> getVariablesValue(ISubmittedObject submittedFormObject) {
        return ((ISubmittedFormElement) this.getParent()).getVariablesValue(submittedFormObject);
    }

    @Override
    public void setVariableValue(String varName, Object value) {
        setVariableValue(this, varName, value);
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
    public void setVariableValue(ISubmittedObject submittedFormObject, String varName, Object value) {
        ((ISubmittedFormElement) getParent()).setVariableValue(submittedFormObject, varName, value);
    }

    @Override
    public String generateXML(String tabs) {
        String xmlFile = tabs + "<" + this.getClass().getSimpleName() + " name=\"" + getTag() + "\"" + ">\n";
        // Generate variables value
        xmlFile += tabs + "\t<variables>\n";
        DroolsSubmittedLogger.debug(this.getClass().getName(),
                "Variables values for '" + this.getName() + "' are '" + getVariablesValue() + "'.");
        if (getVariablesValue() != null) {
            for (Entry<String, Object> child : getVariablesValue().entrySet()) {
                xmlFile += tabs + "\t\t<" + child.getKey() + "><![CDATA[" + child.getValue().toString() + "]]></"
                        + child.getKey() + ">\n";
            }
        }
        xmlFile += tabs + "\t</variables>\n";
        // Generate children nodes
        xmlFile += tabs + "\t<children>\n";
        for (ISubmittedObject child : getChildren()) {
            xmlFile += ((ISubmittedFormElement) child).generateXML(tabs + "\t\t");
        }
        xmlFile += tabs + "\t</children>\n";
        xmlFile += tabs + "</" + this.getClass().getSimpleName() + ">\n";
        DroolsSubmittedLogger.debug(this.getClass().getName(),
                "XML Generated for '" + this.getName() + "' is:\n" + xmlFile);
        return xmlFile;
    }

    @Override
    public String getName() {
        return getTag();
    }

    @Override
    public String getOriginalValue() {
        return "";
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public Map<String, Object> getVariablesValue() {
        return getVariablesValue(this);
    }

    @Override
    public Object getVariableValue(ISubmittedObject submittedFormObject, String varName) {
        return ((ISubmittedFormElement) this.getParent()).getVariableValue(submittedFormObject, varName);
    }
}
