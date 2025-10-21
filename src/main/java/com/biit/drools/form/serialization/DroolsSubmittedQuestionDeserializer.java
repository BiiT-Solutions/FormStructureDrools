package com.biit.drools.form.serialization;

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

import com.biit.drools.form.DroolsSubmittedQuestion;
import com.biit.form.jackson.serialization.ObjectMapperFactory;
import com.biit.form.submitted.serialization.jackson.SubmittedObjectDeserializer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.io.Serial;
import java.util.Set;

public class DroolsSubmittedQuestionDeserializer extends SubmittedObjectDeserializer<DroolsSubmittedQuestion> {

    @Serial
    private static final long serialVersionUID = 5014614795593797332L;

    @Override
    public void deserialize(DroolsSubmittedQuestion element, JsonNode jsonObject, DeserializationContext context) throws IOException {
        super.deserialize(element, jsonObject, context);

        // Answers deserialization

        final JsonNode answersJson = jsonObject.get("answers");
        if (answersJson != null) {
            final Set<String> children = ObjectMapperFactory.getObjectMapper().readValue(answersJson.toPrettyString(),
                    new TypeReference<>() {
                    });
            element.setAnswers(children);
        }
    }
}
