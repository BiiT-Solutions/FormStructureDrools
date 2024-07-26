package com.biit.drools.variables;

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
