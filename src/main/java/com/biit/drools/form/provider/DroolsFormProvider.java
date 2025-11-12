package com.biit.drools.form.provider;

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

import com.biit.drools.form.DroolsForm;
import com.biit.drools.form.DroolsSubmittedCategory;
import com.biit.drools.form.DroolsSubmittedForm;
import com.biit.drools.form.DroolsSubmittedGroup;
import com.biit.drools.form.DroolsSubmittedQuestion;
import com.biit.form.entity.BaseCategory;
import com.biit.form.entity.BaseForm;
import com.biit.form.entity.BaseGroup;
import com.biit.form.entity.BaseQuestionWithValue;
import com.biit.form.entity.TreeObject;
import com.biit.form.result.FormResult;
import com.biit.form.submitted.ISubmittedObject;
import com.biit.form.submitted.ISubmittedQuestion;
import com.biit.form.submitted.implementation.SubmittedCategory;
import com.biit.form.submitted.implementation.SubmittedGroup;
import com.biit.form.submitted.implementation.SubmittedObject;
import com.biit.form.submitted.implementation.SubmittedQuestion;

import java.util.List;
import java.util.Set;


/***
 * Helper for creating DroolsForm structure from different inputs.
 */
public final class DroolsFormProvider {

    private DroolsFormProvider() {

    }

    public static DroolsForm createStructure(FormResult formResult) {
        final DroolsForm droolsForm = createStructure((BaseForm) formResult);
        //droolsForm.setSubmittedBy(formResult.getSubmittedBy());
        return droolsForm;
    }

    public static DroolsForm createStructure(BaseForm formResult) {
        final DroolsSubmittedForm droolsForm = new DroolsSubmittedForm("", formResult.getLabel());
        createSubmittedFromStructure(formResult, droolsForm);
        return new DroolsForm(droolsForm);
    }

    private static void createSubmittedFromStructure(TreeObject treeObject, ISubmittedObject parent) {
        for (TreeObject child : treeObject.getChildren()) {
            if (child instanceof BaseCategory) {
                final SubmittedObject droolsCategory = createCategory(parent, child.getName());
                createSubmittedFromStructure(child, droolsCategory);
            } else if (child instanceof BaseGroup) {
                final SubmittedObject droolsGroup = createGroup(parent, child.getName());
                createSubmittedFromStructure(child, droolsGroup);
            } else if (child instanceof BaseQuestionWithValue) {
                final List<String> answers = ((BaseQuestionWithValue) child).getQuestionValues();
                if (answers != null && !answers.isEmpty()) {
                    final ISubmittedQuestion question = (ISubmittedQuestion) createQuestion(parent, child.getName());
                    answers.forEach(question::addAnswer);
                }
            }
        }
    }

    private static SubmittedObject createCategory(ISubmittedObject parent, String tag) {
        final SubmittedCategory category = new DroolsSubmittedCategory(tag);
        parent.addChild(category);
        return category;
    }

    private static SubmittedObject createGroup(ISubmittedObject parent, String tag) {
        final SubmittedGroup group = new DroolsSubmittedGroup(tag);
        parent.addChild(group);
        return group;
    }

    private static SubmittedObject createQuestion(ISubmittedObject parent, String tag) {
        final SubmittedQuestion question = new DroolsSubmittedQuestion(tag);
        parent.addChild(question);
        return question;
    }

    public static DroolsForm createStructure(ISubmittedObject submittedForm) {
        final DroolsSubmittedForm droolsForm = new DroolsSubmittedForm("", submittedForm.getTag());
        createSubmittedFromStructure(submittedForm, droolsForm);
        //droolsForm.setSubmittedBy(submittedForm.getSubmittedBy());
        return new DroolsForm(droolsForm);
    }

    private static void createSubmittedFromStructure(ISubmittedObject submittedObject, SubmittedObject parent) {
        for (ISubmittedObject child : submittedObject.getChildren()) {
            if (child instanceof DroolsSubmittedCategory) {
                final SubmittedObject droolsCategory = createCategory(parent, child.getTag());
                createSubmittedFromStructure(child, droolsCategory);
            } else if (child instanceof DroolsSubmittedGroup) {
                final SubmittedObject droolsGroup = createGroup(parent, child.getTag());
                createSubmittedFromStructure(child, droolsGroup);
            } else if (child instanceof DroolsSubmittedQuestion) {
                final Set<String> answers = ((DroolsSubmittedQuestion) child).getAnswers();
                answers.forEach(answer -> {
                    final ISubmittedQuestion question = (ISubmittedQuestion) createQuestion(parent, child.getTag());
                    question.addAnswer(answer);
                });
            }
        }
    }
}
