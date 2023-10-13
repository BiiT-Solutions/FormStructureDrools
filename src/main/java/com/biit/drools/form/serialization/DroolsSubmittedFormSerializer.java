package com.biit.drools.form.serialization;

import com.biit.drools.form.DroolsSubmittedForm;
import com.biit.form.submitted.serialization.jackson.SubmittedObjectSerializer;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class DroolsSubmittedFormSerializer extends SubmittedObjectSerializer<DroolsSubmittedForm> {

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void serialize(DroolsSubmittedForm src, JsonGenerator jgen) throws IOException {
        super.serialize(src, jgen);
        if (src.getApplicationName() != null) {
            jgen.writeStringField("applicationName", src.getApplicationName());
        }
        if (src.getOrganizationId() != null) {
            jgen.writeNumberField("organizationId", src.getOrganizationId());
        }
        if (src.getVersion() != null) {
            jgen.writeNumberField("version", src.getVersion());
        }
        if (src.getFormVariables() != null) {
            jgen.writeStringField("formVariables", objectMapper.writeValueAsString(src.getFormVariables()));
        }
    }
}
