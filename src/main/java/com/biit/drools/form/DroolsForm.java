package com.biit.drools.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.biit.form.entity.IQuestionWithAnswers;
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
public class DroolsForm implements ISubmittedForm, Serializable {
	private static final long serialVersionUID = -507044452465253286L;
	private ISubmittedForm droolsSubmittedform;
	private final String label;

	public DroolsForm(ISubmittedForm droolsSubmittedform) {
		this.droolsSubmittedform = droolsSubmittedform;
		this.label = droolsSubmittedform.getName();
	}

	public String getLabel() {
		return label;
	}

	public ISubmittedForm getDroolsSubmittedForm() {
		return droolsSubmittedform;
	}

	public ISubmittedQuestion getQuestion(String categoryName, String questionName) {
		return (ISubmittedQuestion) droolsSubmittedform.getChild(ISubmittedCategory.class, categoryName).getChild(ISubmittedQuestion.class, questionName);
	}

	@Override
	public String getName() {
		return getDroolsSubmittedForm().getName();
	}

	public String getApplicationName() {
		return getDroolsSubmittedForm().getApplicationName();
	}

	public String getId() {
		return getDroolsSubmittedForm().getId();
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
	public <T> List<T> getChildren(Class<T> type) {
		return getDroolsSubmittedForm().getChildren(type);
	}

	@Override
	public String toString() {
		return getDroolsSubmittedForm().toString();
	}

	@Override
	public String getPathName() {
		return getDroolsSubmittedForm().getPathName();
	}

	@Override
	public Integer getIndex(ISubmittedObject child) {
		return getDroolsSubmittedForm().getIndex(child);
	}

	@Override
	public int compareTo(ISubmittedObject arg0) {
		return getDroolsSubmittedForm().compareTo(arg0);
	}

	@Override
	public int getLevel() {
		return getDroolsSubmittedForm().getLevel();
	}

	@Override
	public ISubmittedObject getChild(List<String> subList) {
		return getDroolsSubmittedForm().getChild(subList);
	}

	@Override
	public ISubmittedObject getChild(String pathstring) {
		return getDroolsSubmittedForm().getChild(pathstring);
	}

	@Override
	public List<String> getPath() {
		return new ArrayList<>();
	}

	@Override
	public String getXPath() {
		return "/" + this.getClass().getSimpleName() + "[@label='" + getTag() + "']";
	}

	@Override
	public List<IQuestionWithAnswers> getQuestionsWithAnswers() {
		return getChildren(IQuestionWithAnswers.class);
	}
}
