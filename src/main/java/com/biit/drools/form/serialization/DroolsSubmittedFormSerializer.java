package com.biit.drools.form.serialization;

import com.biit.drools.form.DroolsSubmittedForm;
import com.biit.form.submitted.serialization.jackson.SubmittedObjectSerializer;
import com.fasterxml.jackson.core.JsonGenerator;

import java.io.IOException;

public class DroolsSubmittedFormSerializer extends SubmittedObjectSerializer<DroolsSubmittedForm> {

    @Override
    public void serialize(DroolsSubmittedForm src, JsonGenerator jgen) throws IOException {
        super.serialize(src, jgen);
        if (src.getApplicationName() != null) {
            jgen.writeStringField("applicationName", src.getApplicationName());
        }
        if (src.getOrganizationId() != null) {
            jgen.writeStringField("organizationId", String.valueOf(src.getOrganizationId()));
        }
        if (src.getVersion() != null) {
            jgen.writeStringField("version", String.valueOf(src.getVersion()));
        }
    }
}
