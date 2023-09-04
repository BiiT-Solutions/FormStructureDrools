package com.biit.drools.form.serialization;

import com.biit.drools.form.DroolsSubmittedGroup;
import com.biit.form.submitted.serialization.jackson.SubmittedObjectDeserializer;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class DroolsSubmittedGroupDeserializer extends SubmittedObjectDeserializer<DroolsSubmittedGroup> {

    @Override
    public void deserialize(DroolsSubmittedGroup element, JsonNode jsonObject, DeserializationContext context) throws IOException {
        super.deserialize(element, jsonObject, context);
        element.setRepeatable(parseBoolean("repeatable", jsonObject));
        element.setNumberOfIterations(parseInteger("numberOfIterations", jsonObject));
    }
}
