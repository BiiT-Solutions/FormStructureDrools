package com.biit.drools.form;

import com.biit.drools.form.serialization.DroolsSubmittedGroupDeserializer;
import com.biit.drools.form.serialization.DroolsSubmittedGroupSerializer;
import com.biit.drools.log.DroolsSubmittedLogger;
import com.biit.form.submitted.ISubmittedFormElement;
import com.biit.form.submitted.ISubmittedObject;
import com.biit.form.submitted.implementation.SubmittedGroup;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@JsonDeserialize(using = DroolsSubmittedGroupDeserializer.class)
@JsonSerialize(using = DroolsSubmittedGroupSerializer.class)
public class DroolsSubmittedGroup extends SubmittedGroup implements ISubmittedFormElement {

    public DroolsSubmittedGroup() {
        super();
    }


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
        final List<T> children = getChildrenRecursive(type);

        if (children != null && !children.isEmpty()) {
            return getVariableValue(children.get(0), varName);
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
        final StringBuilder xmlFile = new StringBuilder(tabs + "<" + this.getClass().getSimpleName() + " name=\"" + getTag() + "\"" + ">\n");
        // Generate variables value
        xmlFile.append(tabs).append(TabHandler.TAB).append("<variables>\n");
        DroolsSubmittedLogger.debug(this.getClass().getName(),
                "Variables values for '" + this.getName() + "' are '" + getVariablesValue() + "'.");
        if (getVariablesValue() != null) {
            for (Entry<String, Object> child : getVariablesValue().entrySet()) {
                xmlFile.append(tabs).append(TabHandler.TAB).append(TabHandler.TAB).append("<")
                        .append(child.getKey()).append("><![CDATA[")
                        .append(child.getValue().toString()).append("]]></").append(child.getKey()).append(">\n");
            }
        }
        xmlFile.append(tabs).append(TabHandler.TAB).append("</variables>\n");
        // Generate children nodes
        xmlFile.append(tabs).append(TabHandler.TAB).append("<children>\n");
        for (ISubmittedObject child : getChildren()) {
            xmlFile.append(((ISubmittedFormElement) child).generateXML(tabs + TabHandler.TAB + TabHandler.TAB));
        }
        xmlFile.append(tabs).append(TabHandler.TAB).append("</children>\n");
        xmlFile.append(tabs).append("</").append(this.getClass().getSimpleName()).append(">\n");
        DroolsSubmittedLogger.debug(this.getClass().getName(),
                "XML Generated for '" + this.getName() + "' is:\n" + xmlFile);
        return xmlFile.toString();
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
