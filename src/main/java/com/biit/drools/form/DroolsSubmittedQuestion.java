package com.biit.drools.form;

import com.biit.drools.form.serialization.DroolsSubmittedQuestionDeserializer;
import com.biit.drools.form.serialization.DroolsSubmittedQuestionSerializer;
import com.biit.drools.log.DroolsSubmittedLogger;
import com.biit.form.submitted.ISubmittedFormElement;
import com.biit.form.submitted.ISubmittedObject;
import com.biit.form.submitted.implementation.SubmittedQuestion;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

@JsonDeserialize(using = DroolsSubmittedQuestionDeserializer.class)
@JsonSerialize(using = DroolsSubmittedQuestionSerializer.class)
public class DroolsSubmittedQuestion extends SubmittedQuestion implements ISubmittedFormElement {

    // Date format based on the input received by the Orbeon forms
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    public DroolsSubmittedQuestion(String tag) {
        super(tag);
    }

    public Object getAnswer(String answerFormat) {
        return getAnswer(DroolsQuestionFormat.get(answerFormat));
    }

    public Object getAnswer(DroolsQuestionFormat answerFormat) {
        if (answerFormat == null) {
            return getSimpleAnswer();
        }

        final Object parsedValue = null;
        switch (answerFormat) {
            case NUMBER -> {
                if (getAnswers() != null && !getAnswers().isEmpty()) {
                    try {
                        return Double.parseDouble(getAnswers().iterator().next());
                    } catch (Exception e) {
                        return 0.0;
                    }
                } else {
                    return 0.0;
                }
            }
            case POSTAL_CODE -> {
                return answersAsString().toUpperCase();
            }
            case TEXT -> {
                return answersAsString();
            }
            case MULTI_TEXT -> {
                return String.join(",", getAnswers());
            }
            case DATE -> {
                if (getAnswers() != null && !getAnswers().isEmpty()) {
                    try {
                        return new SimpleDateFormat(DATE_FORMAT).parse(getAnswers().iterator().next());

                    } catch (ParseException e) {
                        // Default, create tomorrow's date
                        final Calendar cal = Calendar.getInstance();
                        cal.setTime(new Date());
                        cal.add(Calendar.DAY_OF_YEAR, 1);
                        final Date tomorrow = cal.getTime();
                        return new SimpleDateFormat(DATE_FORMAT).format(tomorrow);
                    }
                } else {
                    // Default, create tomorrow's date
                    final Calendar cal = Calendar.getInstance();
                    cal.setTime(new Date());
                    cal.add(Calendar.DAY_OF_YEAR, 1);
                    final Date tomorrow = cal.getTime();
                    return new SimpleDateFormat(DATE_FORMAT).format(tomorrow);
                }
            }
            case NULL -> {
                return getSimpleAnswer();
            }
            default -> {
            }
        }
        return parsedValue;
    }

    private Object getSimpleAnswer() {
        Object parsedValue;
        try {
            parsedValue = Double.parseDouble(getAnswers().iterator().next());
        } catch (Exception e) {
            try {
                parsedValue = new SimpleDateFormat(DATE_FORMAT).parse(getAnswers().iterator().next());
            } catch (Exception e1) {
                parsedValue = answersAsString();
            }
        }
        return parsedValue;
    }

    @Override
    public boolean isVariableDefined(String varName) {
        return isVariableDefined(this, varName);
    }

    @Override
    public boolean isVariableDefined(Object submittedFormTreeObject, String varName) {
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
    public Object getVariableValue(Object submmitedFormObject, String varName) {
        return ((ISubmittedFormElement) this.getParent()).getVariableValue(submmitedFormObject, varName);
    }

    @Override
    public void setVariableValue(String varName, Object value) {
        setVariableValue(this, varName, value);
    }

    @Override
    public void setVariableValue(Object submmitedFormObject, String varName, Object value) {
        ((ISubmittedFormElement) getParent()).setVariableValue(submmitedFormObject, varName, value);
    }

    @Override
    public String generateXML(String tabs) {
        final StringBuilder xmlFile = new StringBuilder(tabs + "<" + this.getClass().getSimpleName() + " name=\"" + getTag() + "\"" + ">\n");
        // Generate variables value
        xmlFile.append(tabs).append("\t<variables>\n");
        DroolsSubmittedLogger.debug(this.getClass().getName(),
                "Variables values for '" + this.getName() + "' are '" + getVariablesValue() + "'.");
        if (getVariablesValue() != null) {
            for (Entry<String, Object> child : getVariablesValue().entrySet()) {
                xmlFile.append(tabs).append("\t\t<").append(child.getKey()).append("><![CDATA[").append(child.getValue().toString())
                        .append("]]></").append(child.getKey()).append(">\n");
            }
        }
        xmlFile.append(tabs).append("\t</variables>\n");
        // Generate answer values
        xmlFile.append(tabs).append("\t<value>");
        if (getAnswers() != null && !getAnswers().isEmpty()) {
            xmlFile.append("<![CDATA[").append(answersAsString()).append("]]>");
        }
        xmlFile.append("</value>\n");
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
        return answersAsString();
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public HashMap<String, Object> getVariablesValue(Object submmitedFormObject) {
        return ((ISubmittedFormElement) this.getParent()).getVariablesValue(submmitedFormObject);
    }

    @Override
    public HashMap<String, Object> getVariablesValue() {
        return getVariablesValue(this);
    }

    private String answersAsString() {
        final StringBuilder text = new StringBuilder();
        for (String answer : getAnswers()) {
            text.append(answer).append(" ");
        }
        return text.toString().trim();
    }
}
