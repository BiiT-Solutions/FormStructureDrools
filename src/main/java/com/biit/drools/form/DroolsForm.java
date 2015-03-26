package com.biit.drools.form;

import java.util.List;

import com.biit.form.submitted.ISubmittedCategory;
import com.biit.form.submitted.ISubmittedForm;
import com.biit.form.submitted.ISubmittedObject;
import com.biit.form.submitted.ISubmittedQuestion;
import com.biit.form.submitted.implementation.SubmittedForm;

/**
 * Needed to allow drools to manage variables in memory.<br>
 * It is used as the parent class of the droolsSubmittedform, to allow the
 * droolsSubmittedform to manage variables.<br>
 */
public class DroolsForm implements ISubmittedForm {

	private ISubmittedForm droolsSubmittedform;

	public DroolsForm(ISubmittedForm droolsSubmittedform) {
		this.droolsSubmittedform = droolsSubmittedform;
	}

	public ISubmittedForm getDroolsSubmittedForm() {
		return droolsSubmittedform;
	}

	public ISubmittedQuestion getQuestion(String categoryName, String questionName) {
		return (ISubmittedQuestion) droolsSubmittedform.getChild(ISubmittedCategory.class, categoryName).getChild(
				ISubmittedQuestion.class, questionName);
	}

	@Override
	public String getName() {
		return droolsSubmittedform.getName();
	}

	public String getApplicationName() {
		return droolsSubmittedform.getApplicationName();
	}

	public String getId() {
		return droolsSubmittedform.getId();
	}

	public void setDroolsSubmittedForm(SubmittedForm droolsSubmittedform) {
		this.droolsSubmittedform = droolsSubmittedform;
	}

	@Override
	public String getTag() {
		return getDroolsSubmittedForm().getTag();
	}

	@Override
	public void setTag(String tag) {
		getDroolsSubmittedForm().setTag(tag);
	}

	@Override
	public String getText() {
		return getDroolsSubmittedForm().getText();
	}

	@Override
	public void setText(String text) {
		getDroolsSubmittedForm().setText(text);
	}

	@Override
	public ISubmittedObject getParent() {
		return getDroolsSubmittedForm().getParent();
	}

	@Override
	public void setParent(ISubmittedObject parent) {
		getDroolsSubmittedForm().setParent(parent);
	}

	@Override
	public void addChild(ISubmittedObject child) {
		getDroolsSubmittedForm().addChild(child);
	}

	@Override
	public List<ISubmittedObject> getChildren() {
		return getDroolsSubmittedForm().getChildren();
	}

	@Override
	public void setChildren(List<ISubmittedObject> children) {
		getDroolsSubmittedForm().setChildren(children);
	}

	@Override
	public ISubmittedObject getChild(Class<?> type, String tag) {
		return getDroolsSubmittedForm().getChild(type, tag);
	}

	@Override
	public List<ISubmittedObject> getChildren(Class<?> type) {
		return getDroolsSubmittedForm().getChildren(type);
	}

	@Override
	public String toString() {
		return getDroolsSubmittedForm().toString();
	}

	@Override
	public String getPathName() {
		return droolsSubmittedform.getPathName();
	}

	@Override
	public Integer getIndex(ISubmittedObject child) {
		return droolsSubmittedform.getIndex(child);
	}

	@Override
	public int compareTo(ISubmittedObject arg0) {
		return droolsSubmittedform.compareTo(arg0);
	}

	@Override
	public int getLevel() {
		return droolsSubmittedform.getLevel();
	}
}
