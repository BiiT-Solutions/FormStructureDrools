package com.biit.drools.form;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.biit.form.submitted.ISubmittedFormElement;
import com.biit.form.submitted.ISubmittedObject;
import com.biit.form.submitted.implementation.SubmittedQuestion;

public class DroolsSubmittedQuestion extends SubmittedQuestion implements ISubmittedFormElement {

	// Date format based on the input received by the Orbeon forms
	private static final String DATE_FORMAT = "yyyy-MM-dd";

	public DroolsSubmittedQuestion(String tag) {
		super(tag);
	}

	public Object getAnswer(DroolsQuestionsFormat answerFormat) {
		if (answerFormat == null) {
			return null;
		}
		if (answerFormat == null || answerFormat.equals(DroolsQuestionsFormat.NULL)) {
			return getSimpleAnswer();
		}

		Object parsedValue = null;
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
					Calendar cal = Calendar.getInstance();
					cal.setTime(new Date());
					cal.add(Calendar.DAY_OF_YEAR, 1);
					Date tomorrow = cal.getTime();
					return new SimpleDateFormat(DATE_FORMAT).format(tomorrow);
				}
			} else {
				// Default, create tomorrow's date
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date());
				cal.add(Calendar.DAY_OF_YEAR, 1);
				Date tomorrow = cal.getTime();
				return new SimpleDateFormat(DATE_FORMAT).format(tomorrow);
			}
		case NULL:
			return getSimpleAnswer();
		default:
			break;
		}
		return parsedValue;
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
	public Object getVariableValue(Class<?> type, String varName) {
		List<ISubmittedObject> childs = getChildren(type);

		if (childs != null && !childs.isEmpty()) {
			return getVariableValue(childs.get(0), varName);
		}
		return null;
	}

	@Override
	public Object getVariableValue(Class<?> type, String treeObjectName, String varName) {
		ISubmittedObject child = getChild(type, treeObjectName);

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
		String xmlFile = tabs + "<" + this.getClass().getSimpleName() + " name=\"" + getTag() + "\"" + ">\n";
		// Generate variables value
		xmlFile += tabs + "\t<variables>\n";
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
	public HashMap<String, Object> getVariablesValue(Object submmitedFormObject) {
		return ((ISubmittedFormElement) this.getParent()).getVariablesValue(submmitedFormObject);
	}

	@Override
	public HashMap<String, Object> getVariablesValue() {
		return getVariablesValue(this);
	}

	private String answersAsString() {
		String text = "";
		for (String answer : getAnswers()) {
			text += answer + " ";
		}
		return text.trim();
	}
}
