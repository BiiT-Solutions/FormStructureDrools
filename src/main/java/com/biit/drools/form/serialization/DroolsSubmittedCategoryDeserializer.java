package com.biit.drools.form.serialization;

import com.biit.drools.form.DroolsSubmittedCategory;
import com.biit.form.submitted.serialization.jackson.SubmittedObjectDeserializer;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class DroolsSubmittedCategoryDeserializer extends SubmittedObjectDeserializer<DroolsSubmittedCategory> {

    @Override
    public void deserialize(DroolsSubmittedCategory element, JsonNode jsonObject, DeserializationContext context) throws IOException {
        super.deserialize(element, jsonObject, context);
    }
}
