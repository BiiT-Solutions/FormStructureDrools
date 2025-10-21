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

import com.biit.form.jackson.serialization.ObjectMapperFactory;
import com.biit.form.submitted.implementation.SubmittedForm;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Test(groups = "jsonTests")
public class JsonTests {

    private final static String APPLICATION_NAME = "Test";
    private final static String FORM_NAME = "TheForm";
    private final static Integer FORM_VERSION = 3;
    private final static String FORM_ORGANIZATION = "The Organization";


    private DroolsSubmittedForm droolsSubmittedForm;
    private DroolsSubmittedQuestion question;

    private String readFile(String fileName) {
        try {
            return new String(Files.readAllBytes(Paths.get(getClass().getClassLoader()
                    .getResource(fileName).toURI())));
        } catch (Exception e) {
            Assert.fail("Cannot read resource '" + fileName + "'.");
        }
        return null;
    }

    private boolean deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }

    private void checkContent(String content, String resourceFile) {
        try {
            Assert.assertEquals(content.trim(), new String(Files.readAllBytes(Paths.get(getClass().getClassLoader()
                    .getResource(resourceFile).toURI()))).trim());
        } catch (IOException | URISyntaxException e) {
            Assert.fail();
        }
    }

    @BeforeClass
    public void createForm() {
        droolsSubmittedForm = new DroolsSubmittedForm();
        droolsSubmittedForm.setApplicationName(APPLICATION_NAME);
        droolsSubmittedForm.setTag(FORM_NAME);
        droolsSubmittedForm.setVersion(FORM_VERSION);
        droolsSubmittedForm.setOrganization(FORM_ORGANIZATION);

        DroolsSubmittedCategory category = new DroolsSubmittedCategory("The Category");
        droolsSubmittedForm.addChild(category);
        question = new DroolsSubmittedQuestion("The Question");
        question.addAnswer("One");
        question.addAnswer("Two");
        question.addAnswer("Three");
        category.addChild(question);

        droolsSubmittedForm.setVariableValue(question, "var1", 5);
        droolsSubmittedForm.setVariableValue(question, "var2", "asd");
    }

    @Test
    public void toJson() throws IOException {
        String jsonText = ObjectMapperFactory.getObjectMapper().writeValueAsString(droolsSubmittedForm);
        checkContent(jsonText, "droolsSubmittedForm.json");
    }

    @Test(dependsOnMethods = "toJson")
    public void fromJson() throws JsonProcessingException {
        final String jsonText = readFile("droolsSubmittedForm.json");
        final DroolsSubmittedForm droolsSubmittedForm = ObjectMapperFactory.getObjectMapper().readValue(jsonText, DroolsSubmittedForm.class);
        Assert.assertEquals(jsonText, ObjectMapperFactory.getObjectMapper().writeValueAsString(droolsSubmittedForm));
        Assert.assertEquals(droolsSubmittedForm.getFormVariables().size(), 1);
        Assert.assertEquals(droolsSubmittedForm.getFormVariables().get(question.getXPath()).size(), 2);
    }
}
