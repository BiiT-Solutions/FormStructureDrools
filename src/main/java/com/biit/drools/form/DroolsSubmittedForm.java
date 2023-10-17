package com.biit.drools.form;

import com.biit.drools.form.serialization.DroolsSubmittedFormDeserializer;
import com.biit.drools.form.serialization.DroolsSubmittedFormSerializer;
import com.biit.drools.log.DroolsSubmittedLogger;
import com.biit.form.jackson.serialization.ObjectMapperFactory;
import com.biit.form.submitted.ISubmittedCategory;
import com.biit.form.submitted.ISubmittedForm;
import com.biit.form.submitted.ISubmittedFormElement;
import com.biit.form.submitted.ISubmittedObject;
import com.biit.form.submitted.ISubmittedQuestion;
import com.biit.form.submitted.implementation.SubmittedForm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

@JsonDeserialize(using = DroolsSubmittedFormDeserializer.class)
@JsonSerialize(using = DroolsSubmittedFormSerializer.class)
public class DroolsSubmittedForm extends SubmittedForm implements ISubmittedFormElement, Serializable {
    // TreeObject -> VarName --> Value
    private Map<String, Map<String, Object>> formVariables;

    private static final long serialVersionUID = -1289388087219471449L;

    public DroolsSubmittedForm() {
        super("", "");
    }

    public DroolsSubmittedForm(String applicationName, String formName) {
        super(applicationName, formName);
    }

    public DroolsSubmittedForm(String applicationName, String formName, Integer version) {
        super(applicationName, formName, version);
    }

    public DroolsSubmittedForm(String formName) {
        super("", formName);
    }

    @Override
    public String getId() {
        if ((this.getApplicationName() != null) && (this.getName() != null)) {
            return this.getApplicationName() + "/" + this.getName();
        }
        return null;
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
        ISubmittedObject selectedObject = null;
        // Check this element.
        if (type.isInstance(this)) {
            if (this.getTag().equals(treeObjectName)) {
                return this;
            }
        }

        // Check the children.
        selectedObject = getChild(type, treeObjectName);

        if (selectedObject != null) {
            return getVariableValue(selectedObject, varName);
        }
        return null;
    }

    @Override
    public Object getVariableValue(ISubmittedObject submittedFormObject, String varName) {
        if ((formVariables == null) || (formVariables.get(submittedFormObject.getXPath()) == null)) {
            return null;
        }
        return formVariables.get(submittedFormObject.getXPath()).get(varName);
    }

    @Override
    public Object getVariableValue(String varName) {
        return getVariableValue(this, varName);
    }

    @Override
    public boolean isVariableDefined(String varName) {
        // Retrieve the form which will have the variables
        return isVariableDefined(this, varName);
    }

    @Override
    public boolean isVariableDefined(ISubmittedObject submittedFormTreeObject, String varName) {
        return !((formVariables == null) || (formVariables.get(submittedFormTreeObject.getXPath()) == null)
                || (formVariables.get(submittedFormTreeObject.getXPath()).get(varName) == null));
    }

    @Override
    public void setVariableValue(ISubmittedObject submittedFormTreeObject, String varName, Object value) {
        if (value != null) {
            DroolsSubmittedLogger.debug(this.getClass().getName(), "Setting variable '" + varName + "' with value '"
                    + value + "' for '" + submittedFormTreeObject + "'.");
            if (formVariables == null) {
                formVariables = new HashMap<>();
            }
            formVariables.computeIfAbsent(submittedFormTreeObject.getXPath(), k -> new HashMap<>());
            formVariables.get(submittedFormTreeObject.getXPath()).put(varName, value);
        }
    }

    @Override
    public void setVariableValue(String varName, Object value) {
        setVariableValue(this, varName, value);
    }

    public Map<String, Map<String, Object>> getFormVariables() {
        return formVariables;
    }

    /**
     * Only for json generation.
     */
    public void setFormVariables(Map<String, Map<String, Object>> formVariables) {
        this.formVariables = formVariables;
    }

    public Map<String, Map<String, Object>> getFormVariables(Class<?> filter) {
        return formVariables.entrySet().stream().filter(key -> filter.isAssignableFrom(key.getKey().getClass()))
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
    }

    public ISubmittedForm getSubmittedForm() {
        return this;
    }

    public ISubmittedQuestion getQuestion(String categoryName, String questionName) {
        return getChild(ISubmittedCategory.class, categoryName).getChild(ISubmittedQuestion.class,
                questionName);
    }

    @Override
    public String getOriginalValue() {
        return "";
    }

    @Override
    public String generateXML(String tabs) {
        final StringBuilder xmlFile = new StringBuilder("<" + this.getClass().getSimpleName() + " label=\"" + getName() + "\"" + ">\n");
        // Generate variables value
        xmlFile.append("\t<variables>\n");
        DroolsSubmittedLogger.debug(this.getClass().getName(),
                "Variables values for '" + this.getName() + "' are '" + getVariablesValue() + "'.");
        if (getVariablesValue() != null) {
            for (Entry<String, Object> child : getVariablesValue().entrySet()) {
                xmlFile.append("\t\t<").append(child.getKey()).append("><![CDATA[").append(child.getValue().toString())
                        .append("]]></").append(child.getKey()).append(">\n");
            }
        }
        xmlFile.append("\t</variables>\n");
        // Generate children nodes
        xmlFile.append("\t<children>\n");
        for (ISubmittedObject child : getChildren()) {
            xmlFile.append(((ISubmittedFormElement) child).generateXML("    "));
        }
        xmlFile.append("\t</children>\n");
        xmlFile.append("</").append(this.getClass().getSimpleName()).append(">");
        DroolsSubmittedLogger.debug(this.getClass().getName(),
                "XML Generated for '" + this.getName() + "' is:\n" + xmlFile);
        return xmlFile.toString();
    }

    public String generateXML() {
        return generateXML("");
    }

    @Override
    public Map<String, Object> getVariablesValue(ISubmittedObject submittedFormTreeObject) {
        if (formVariables == null) {
            return null;
        }
        DroolsSubmittedLogger.debug(this.getClass().getName(), "Form variables for '" + submittedFormTreeObject
                + "' are '" + formVariables.get(submittedFormTreeObject) + "'.");
        return formVariables.get(submittedFormTreeObject.getXPath());
    }

    @Override
    public Map<String, Object> getVariablesValue() {
        return getVariablesValue(this);
    }

    @Override
    public String getXPath() {
        return "/" + this.getClass().getSimpleName() + "[@label='" + getTag() + "']";
    }

    public static DroolsSubmittedForm getFromJson(String jsonString) throws JsonProcessingException {
        return ObjectMapperFactory.getObjectMapper().readValue(jsonString, DroolsSubmittedForm.class);
    }
}
