package com.biit.drools.form.serialization;

import com.biit.drools.form.DroolsSubmittedForm;
import com.biit.form.jackson.serialization.BaseStorableObjectDeserializer;
import com.biit.form.jackson.serialization.ObjectMapperFactory;
import com.biit.form.submitted.serialization.jackson.SubmittedObjectSerializer;
import com.fasterxml.jackson.core.JsonGenerator;

import java.io.IOException;
import java.io.Serial;

public class DroolsSubmittedFormSerializer extends SubmittedObjectSerializer<DroolsSubmittedForm> {

    @Serial
    private static final long serialVersionUID = 6988672712296424814L;

    @Override
    public void serialize(DroolsSubmittedForm src, JsonGenerator jgen) throws IOException {
        super.serialize(src, jgen);
        if (src.getApplicationName() != null) {
            jgen.writeStringField("applicationName", src.getApplicationName());
        }
        if (src.getOrganization() != null) {
            jgen.writeStringField("organization", src.getOrganization());
        }
        if (src.getSubmittedBy() != null) {
            jgen.writeStringField("submittedBy", src.getSubmittedBy());
        }
        if (src.getSubmittedAt() != null) {
            jgen.writeStringField("submittedAt", src.getSubmittedAt().format(BaseStorableObjectDeserializer.TIMESTAMP_FORMATTER));
        }
        if (src.getVersion() != null) {
            jgen.writeNumberField("version", src.getVersion());
        } else {
            jgen.writeNumberField("version", 1);
        }
        if (src.getFormVariables() != null) {
            jgen.writeStringField("formVariables", ObjectMapperFactory.getObjectMapper()
                    .writeValueAsString(src.getFormVariables()));
        }
    }
}
