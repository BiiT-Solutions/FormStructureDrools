package com.biit.drools.form.provider;

import com.biit.drools.form.DroolsForm;
import com.biit.drools.form.DroolsSubmittedCategory;
import com.biit.drools.form.DroolsSubmittedForm;
import com.biit.drools.form.DroolsSubmittedGroup;
import com.biit.drools.form.DroolsSubmittedQuestion;
import com.biit.drools.form.DroolsSubmittedSystemField;
import com.biit.form.entity.BaseCategory;
import com.biit.form.entity.BaseForm;
import com.biit.form.entity.BaseGroup;
import com.biit.form.entity.BaseQuestionWithValue;
import com.biit.form.entity.TreeObject;
import com.biit.form.result.FormResult;
import com.biit.form.submitted.ISubmittedQuestion;
import com.biit.form.submitted.implementation.SubmittedCategory;
import com.biit.form.submitted.implementation.SubmittedForm;
import com.biit.form.submitted.implementation.SubmittedGroup;
import com.biit.form.submitted.implementation.SubmittedObject;
import com.biit.form.submitted.implementation.SubmittedQuestion;
import com.biit.form.submitted.implementation.SubmittedSystemField;

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
        droolsForm.setSubmittedBy(formResult.getSubmittedBy());
        return droolsForm;
    }

    public static DroolsForm createStructure(BaseForm formResult) {
        final DroolsSubmittedForm droolsForm = new DroolsSubmittedForm("", formResult.getLabel());
        createSubmittedFromStructure(formResult, droolsForm);
        return new DroolsForm(droolsForm);
    }

    private static void createSubmittedFromStructure(TreeObject treeObject, SubmittedObject parent) {
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

    private static SubmittedCategory createCategory(SubmittedObject parent, String tag) {
        final SubmittedCategory category = new DroolsSubmittedCategory(tag);
        parent.addChild(category);
        return category;
    }

    private static SubmittedGroup createGroup(SubmittedObject parent, String tag) {
        final SubmittedGroup group = new DroolsSubmittedGroup(tag);
        parent.addChild(group);
        return group;
    }

    private static SubmittedQuestion createQuestion(SubmittedObject parent, String tag) {
        final SubmittedQuestion question = new DroolsSubmittedQuestion(tag);
        parent.addChild(question);
        return question;
    }

    private static SubmittedSystemField createSystemField(SubmittedObject parent, String tag) {
        final SubmittedSystemField systemField = new DroolsSubmittedSystemField(tag);
        parent.addChild(systemField);
        return systemField;
    }

    public static DroolsForm createStructure(SubmittedForm submittedForm) {
        final DroolsSubmittedForm droolsForm = new DroolsSubmittedForm("", submittedForm.getTag());
        createSubmittedFromStructure(submittedForm, droolsForm);
        droolsForm.setSubmittedBy(submittedForm.getSubmittedBy());
        droolsForm.setSubmittedAt(submittedForm.getSubmittedAt());
        return new DroolsForm(droolsForm);
    }

    private static void createSubmittedFromStructure(SubmittedObject submittedObject, SubmittedObject parent) {
        for (SubmittedObject child : submittedObject.getChildren()) {
            if (child instanceof DroolsSubmittedCategory) {
                final SubmittedObject droolsCategory = createCategory(parent, child.getTag());
                createSubmittedFromStructure(child, droolsCategory);
            } else if (child instanceof DroolsSubmittedGroup) {
                final SubmittedObject droolsGroup = createGroup(parent, child.getTag());
                createSubmittedFromStructure(child, droolsGroup);
            } else if (child instanceof DroolsSubmittedQuestion) {
                final Set<String> answers = ((DroolsSubmittedQuestion) child).getAnswers();
                answers.forEach(answer -> {
                    final ISubmittedQuestion question = createQuestion(parent, child.getTag());
                    question.addAnswer(answer);
                });
            } else if (child instanceof DroolsSubmittedSystemField submittedSystemField) {
                final SubmittedSystemField droolsSystemField = createSystemField(parent, child.getTag());
                droolsSystemField.setValue(submittedSystemField.getValue());
            }
        }
    }
}
