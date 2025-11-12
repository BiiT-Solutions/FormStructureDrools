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
import com.biit.form.submitted.implementation.SubmittedQuestion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class DroolsSubmittedQuestion extends SubmittedQuestion implements ISubmittedFormElement {

    // Date format based on the input received by the Orbeon forms
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    public DroolsSubmittedQuestion(String tag) {
        super(tag);
    }

    public Object getAnswer(String answerFormat) {
        final DroolsQuestionFormat droolsQuestionFormat = DroolsQuestionFormat.get(answerFormat);
        final Object answer = getAnswer(droolsQuestionFormat);
        if (answer != null) {
            return answer;
        }
        //Return empty string rather than null. To avoid NPE in drools.
        return "";
    }

    public Object getAnswer(DroolsQuestionFormat answerFormat) {
        if (answerFormat == null) {
            return null;
        }
        if (answerFormat == null || answerFormat.equals(DroolsQuestionFormat.NULL)) {
            return getSimpleAnswer();
        }

        switch (answerFormat) {
            case NUMBER:
                if (getAnswers() != null && !getAnswers().isEmpty()) {
                    try {
                        return Double.parseDouble(getAnswers().iterator().next());
                    } catch (Exception e) {
                        return 0.0;
                    }
                } else {
                    return 0.0;
                }

            case POSTAL_CODE:
                return answersAsString().toUpperCase();
            case TEXT:
                return answersAsString();
            case MULTI_TEXT:
                return getAnswers();
            case DATE:
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
            case NULL:
                return getSimpleAnswer();
            default:
                break;
        }
        //Return empty string rather than null. To avoid NPE in drools.
        return "";
    }

    private Object getSimpleAnswer() {
        Object parsedValue = null;
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
    public Object getVariableValue(ISubmittedObject submittedFormObject, String varName) {
        return ((ISubmittedFormElement) this.getParent()).getVariableValue(submittedFormObject, varName);
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
        // Generate answer values
        xmlFile += tabs + "\t<value>";
        if (getAnswers() != null && !getAnswers().isEmpty()) {
            xmlFile += "<![CDATA[" + answersAsString() + "]]>";
        }
        xmlFile += "</value>\n";
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
        return answersAsString();
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public Map<String, Object> getVariablesValue() {
        return getVariablesValue(this);
    }

    private String answersAsString() {
        StringBuilder text = new StringBuilder();
        if (getAnswers() != null) {
            for (String answer : getAnswers()) {
                if (answer != null) {
                    text.append(answer).append(" ");
                }
            }
        }
        return text.toString().trim();
    }
}
