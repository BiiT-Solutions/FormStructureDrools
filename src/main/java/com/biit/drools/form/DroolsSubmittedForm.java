package com.biit.drools.form;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.biit.drools.log.DroolsSubmittedLogger;
import com.biit.form.submitted.ISubmittedCategory;
import com.biit.form.submitted.ISubmittedForm;
import com.biit.form.submitted.ISubmittedFormElement;
import com.biit.form.submitted.ISubmittedObject;
import com.biit.form.submitted.ISubmittedQuestion;
import com.biit.form.submitted.exceptions.CategoryDoesNotExistException;
import com.biit.form.submitted.exceptions.QuestionDoesNotExistException;
import com.biit.form.submitted.implementation.SubmittedForm;

public class DroolsSubmittedForm extends SubmittedForm implements ISubmittedFormElement, Serializable {
	// TreeObject -> VarName --> Value
	private HashMap<Object, HashMap<String, Object>> formVariables;

	private static final long serialVersionUID = -1289388087219471449L;

	public DroolsSubmittedForm() {
		super("", "");
	}

	public DroolsSubmittedForm(String applicationName, String formName) {
		super(applicationName, formName);
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
		List<T> childs = getChildren(type);

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
		if (selectedObject == null) {
			selectedObject = getChild(type, treeObjectName);
		}

		if (selectedObject != null) {
			return getVariableValue(selectedObject, varName);
		}
		return null;
	}

	@Override
	public Object getVariableValue(Object submmitedFormObject, String varName) {
		if ((formVariables == null) || (formVariables.get(submmitedFormObject) == null)) {
			return null;
		}
		return formVariables.get(submmitedFormObject).get(varName);
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
	public boolean isVariableDefined(Object submittedFormTreeObject, String varName) {
		return !((formVariables == null) || (formVariables.get(submittedFormTreeObject) == null)
				|| (formVariables.get(submittedFormTreeObject).get(varName) == null));
	}

	@Override
	public void setVariableValue(Object submittedFormTreeObject, String varName, Object value) {
		if (value != null) {
			DroolsSubmittedLogger.debug(this.getClass().getName(), "Setting variable '" + varName + "' with value '"
					+ value + "' for '" + submittedFormTreeObject + "'.");
			if (formVariables == null) {
				formVariables = new HashMap<Object, HashMap<String, Object>>();
			}
			if (formVariables.get(submittedFormTreeObject) == null) {
				formVariables.put(submittedFormTreeObject, new HashMap<String, Object>());
			}
			formVariables.get(submittedFormTreeObject).put(varName, value);
		}
	}

	@Override
	public void setVariableValue(String varName, Object value) {
		setVariableValue(this, varName, value);
	}

	@Override
	public String toString() {
		return getName();
	}

	public HashMap<Object, HashMap<String, Object>> getFormVariables() {
		return formVariables;
	}

	public ISubmittedForm getSubmittedForm() {
		return this;
	}

	public ISubmittedQuestion getQuestion(String categoryName, String questionName)
			throws QuestionDoesNotExistException, CategoryDoesNotExistException {
		return (ISubmittedQuestion) getChild(ISubmittedCategory.class, categoryName).getChild(ISubmittedQuestion.class,
				questionName);
	}

	@Override
	public String getOriginalValue() {
		return "";
	}

	@Override
	public String generateXML(String tabs) {
		String xmlFile = "<" + this.getClass().getSimpleName() + " label=\"" + getName() + "\"" + ">\n";
		// Generate variables value
		xmlFile += "\t<variables>\n";
		DroolsSubmittedLogger.debug(this.getClass().getName(),
				"Variables values for '" + this.getName() + "' are '" + getVariablesValue() + "'.");
		if (getVariablesValue() != null) {
			for (Entry<String, Object> child : getVariablesValue().entrySet()) {
				xmlFile += "\t\t<" + child.getKey() + "><![CDATA[" + child.getValue().toString() + "]]></"
						+ child.getKey() + ">\n";
			}
		}
		xmlFile += "\t</variables>\n";
		// Generate children nodes
		xmlFile += "\t<children>\n";
		for (ISubmittedObject child : getChildren()) {
			xmlFile += ((ISubmittedFormElement) child).generateXML("\t\t");
		}
		xmlFile += "\t</children>\n";
		xmlFile += "</" + this.getClass().getSimpleName() + ">";
		DroolsSubmittedLogger.debug(this.getClass().getName(),
				"XML Generated for '" + this.getName() + "' is:\n" + xmlFile);
		return xmlFile;
	}

	public String generateXML() {
		return generateXML("");
	}

	@Override
	public HashMap<String, Object> getVariablesValue(Object submittedFormTreeObject) {
		if (formVariables == null) {
			return null;
		}
		DroolsSubmittedLogger.debug(this.getClass().getName(), "Form variables for '" + submittedFormTreeObject
				+ "' are '" + formVariables.get(submittedFormTreeObject) + "'.");
		return formVariables.get(submittedFormTreeObject);
	}

	@Override
	public HashMap<String, Object> getVariablesValue() {
		return getVariablesValue(this);
	}

	@Override
	public String getXPath() {
		return "/" + this.getClass().getSimpleName() + "[@label='" + getTag() + "']";
	}
}
