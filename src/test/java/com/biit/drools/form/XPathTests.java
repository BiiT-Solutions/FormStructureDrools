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

import com.biit.form.submitted.implementation.SubmittedQuestion;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Test(groups = "xpath")
public class XPathTests {

    private static final String XPATH = "/DroolsSubmittedForm[@label='The 5 Frustrations on Teamworking']/children/DroolsSubmittedCategory[@name='frustrations']/children/DroolsSubmittedQuestion[@name='failing_goals']";
    private static final String FORM_AS_JSON = "The 5 Frustrations on Teamworking 1.json";

    @Test
    public void checkQuestionSearch() throws IOException, URISyntaxException {
        // Load form from json file in resources.
        final String text = new String(Files.readAllBytes(Paths.get(getClass().getClassLoader().getResource(FORM_AS_JSON).toURI())));
        DroolsSubmittedForm form = DroolsSubmittedForm.getFromJson(text);
        Assert.assertNotNull(form);

        final SubmittedQuestion question = form.getElement(XPATH);
        Assert.assertNotNull(question);
        Assert.assertEquals(question.getText(), "failing_goals");
    }
}
