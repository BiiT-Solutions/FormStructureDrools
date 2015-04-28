package com.biit.drools.form;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.biit.form.submitted.ISubmittedFormElement;
import com.biit.form.submitted.ISubmittedObject;
import com.biit.form.submitted.implementation.SubmittedCategory;

public class DroolsSubmittedCategory extends SubmittedCategory implements ISubmittedFormElement {

	public DroolsSubmittedCategory(String tag) {
		super(tag);
	}

	public boolean isScoreNotSet(String varName) {
		return !isVariableDefined(varName);
	}

	@Override
	public boolean isVariableDefined(String varName) {
		// Retrieve the form which will have the variables
		return isVariableDefined(this, varName);
	}

	@Override
	public boolean isVariableDefined(Object submittedFormTreeObject, String varName) {
		// Retrieve the form which will have the variables
		return ((ISubmittedFormElement) getParent()).isVariableDefined(submittedFormTreeObject, varName);
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
		xmlFile += tabs + "\t<variables>";
		if (getVariablesValue() != null) {
			for (Entry<String, Object> child : getVariablesValue().entrySet()) {
				xmlFile += tabs + "\t\t<" + child.getKey() + ">" + child.getValue().toString() + "</" + child.getKey()
						+ ">";
			}
		}
		xmlFile += tabs + "\t</variables>";
		// Generate children nodes
		xmlFile += tabs + "\t<children>";
		for (ISubmittedObject child : getChildren()) {
			xmlFile += ((ISubmittedFormElement) child).generateXML(tabs + "\t");
		}
		xmlFile += tabs + "\t</children>";
		xmlFile += tabs + "</" + this.getClass().getSimpleName() + ">";
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
	public HashMap<String, Object> getVariablesValue(Object submmitedFormObject) {
		return ((ISubmittedFormElement) this.getParent()).getVariablesValue(submmitedFormObject);
	}

	@Override
	public HashMap<String, Object> getVariablesValue() {
		return getVariablesValue(this);
	}
}
