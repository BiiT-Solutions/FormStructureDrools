package com.biit.drools.form.serialization;

import com.biit.drools.form.DroolsSubmittedForm;
import com.biit.form.jackson.serialization.ObjectMapperFactory;
import com.biit.form.submitted.serialization.jackson.SubmittedObjectDeserializer;
import com.fasterxml.jackson.core.type.TypeReference;
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
        element.setName(parseString("name", jsonObject));
        element.setTag(parseString("tag", jsonObject));
        element.setSubmittedBy(parseString("submittedBy", jsonObject));
        if (jsonObject.get("formVariables") != null) {
            element.setFormVariables(ObjectMapperFactory.getObjectMapper().readValue(jsonObject.get("formVariables").textValue(), new TypeReference<>() {
            }));
        }
    }
}

