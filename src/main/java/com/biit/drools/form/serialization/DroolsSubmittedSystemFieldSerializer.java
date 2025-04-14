package com.biit.drools.form.serialization;

import com.biit.drools.form.DroolsSubmittedSystemField;
import com.biit.form.submitted.serialization.jackson.SubmittedObjectSerializer;
import com.fasterxml.jackson.core.JsonGenerator;

import java.io.IOException;
import java.io.Serial;

public class DroolsSubmittedSystemFieldSerializer extends SubmittedObjectSerializer<DroolsSubmittedSystemField> {

    @Serial
    private static final long serialVersionUID = 1642862259844422396L;

    @Override
    public void serialize(DroolsSubmittedSystemField src, JsonGenerator jgen) throws IOException {
        super.serialize(src, jgen);
        jgen.writeStringField("value", String.valueOf(src.getValue()));
    }
}
