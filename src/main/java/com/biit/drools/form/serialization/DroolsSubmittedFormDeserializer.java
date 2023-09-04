package com.biit.drools.form.serialization;

import com.biit.drools.form.DroolsSubmittedForm;
import com.biit.form.submitted.serialization.jackson.SubmittedObjectDeserializer;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class DroolsSubmittedFormDeserializer extends SubmittedObjectDeserializer<DroolsSubmittedForm> {

    @Override
    public void deserialize(DroolsSubmittedForm element, JsonNode jsonObject, DeserializationContext context) throws IOException {
        super.deserialize(element, jsonObject, context);
        element.setApplicationName(parseString("applicationName", jsonObject));
        element.setOrganizationId(parseLong("organizationId", jsonObject));
        element.setVersion(parseInteger("version", jsonObject));
    }
}

