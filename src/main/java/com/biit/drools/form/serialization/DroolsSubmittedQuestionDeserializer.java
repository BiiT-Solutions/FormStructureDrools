package com.biit.drools.form.serialization;

import com.biit.drools.form.DroolsSubmittedQuestion;
import com.biit.form.jackson.serialization.ObjectMapperFactory;
import com.biit.form.submitted.serialization.jackson.SubmittedObjectDeserializer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.Set;

public class DroolsSubmittedQuestionDeserializer extends SubmittedObjectDeserializer<DroolsSubmittedQuestion> {

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
