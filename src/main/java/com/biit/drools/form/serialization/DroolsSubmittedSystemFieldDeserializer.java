package com.biit.drools.form.serialization;

import com.biit.drools.form.DroolsSubmittedSystemField;
import com.biit.form.submitted.serialization.jackson.SubmittedObjectDeserializer;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.io.Serial;

public class DroolsSubmittedSystemFieldDeserializer extends SubmittedObjectDeserializer<DroolsSubmittedSystemField> {

    @Serial
    private static final long serialVersionUID = -6997489234715510239L;

    @Override
    public void deserialize(DroolsSubmittedSystemField element, JsonNode jsonObject, DeserializationContext context) throws IOException {
        super.deserialize(element, jsonObject, context);
        element.setValue(parseString("value", jsonObject));
    }
}
