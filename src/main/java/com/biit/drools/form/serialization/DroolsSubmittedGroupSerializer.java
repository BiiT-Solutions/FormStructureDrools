package com.biit.drools.form.serialization;

import com.biit.drools.form.DroolsSubmittedGroup;
import com.biit.form.submitted.serialization.jackson.SubmittedObjectSerializer;
import com.fasterxml.jackson.core.JsonGenerator;

import java.io.IOException;
import java.io.Serial;

public class DroolsSubmittedGroupSerializer extends SubmittedObjectSerializer<DroolsSubmittedGroup> {

    @Serial
    private static final long serialVersionUID = 2527241920889459863L;

    @Override
    public void serialize(DroolsSubmittedGroup src, JsonGenerator jgen) throws IOException {
        super.serialize(src, jgen);
        jgen.writeStringField("repeatable", String.valueOf(src.isRepeatable()));
        jgen.writeStringField("numberOfIterations", String.valueOf(src.getNumberOfIterations()));
    }
}
