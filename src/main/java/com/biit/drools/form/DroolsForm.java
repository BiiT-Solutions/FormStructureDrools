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

import com.biit.form.entity.IQuestionWithAnswers;
import com.biit.form.submitted.ISubmittedCategory;
import com.biit.form.submitted.ISubmittedForm;
import com.biit.form.submitted.ISubmittedObject;
import com.biit.form.submitted.ISubmittedQuestion;
import com.biit.form.submitted.implementation.SubmittedForm;
import com.biit.form.submitted.implementation.SubmittedObject;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Needed to allow drools to manage variables in memory.<br>
 * It is used as the parent class of the droolsSubmittedform, to allow the
 * droolsSubmittedform to manage variables.<br>
 */
public class DroolsForm implements ISubmittedForm, Serializable {
    private static final long serialVersionUID = -507044452465253286L;
    private ISubmittedForm droolsSubmittedform;
    private final String label;

    private String submittedBy;
    private LocalDateTime submittedAt;

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
        return (ISubmittedQuestion) droolsSubmittedform.getChild(ISubmittedCategory.class, categoryName)
                .getChild(ISubmittedQuestion.class, questionName);
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
    public void setParent(SubmittedObject parent) {
        getDroolsSubmittedForm().setParent(parent);
    }

    @Override
    public void addChild(SubmittedObject child) {
        getDroolsSubmittedForm().addChild(child);
    }

    @Override
    public List<SubmittedObject> getChildren() {
        return getDroolsSubmittedForm().getChildren();
    }

    @Override
    public void setChildren(List<SubmittedObject> children) {
        getDroolsSubmittedForm().setChildren(children);
    }

    @Override
    public <T> T getChild(Class<T> type, String tag) {
        return getDroolsSubmittedForm().getChild(type, tag);
    }

    @Override
    public <T> List<T> getChildrenRecursive(Class<T> type) {
        return getDroolsSubmittedForm().getChildrenRecursive(type);
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
        return getChildrenRecursive(IQuestionWithAnswers.class);
    }

    @Override
    public String toJson() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SubmittedForm fromJson(String jsonString) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getSubmittedBy() {
        return submittedBy;
    }

    public void setSubmittedBy(String submittedBy) {
        this.submittedBy = submittedBy;
    }

    @Override
    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }

}
