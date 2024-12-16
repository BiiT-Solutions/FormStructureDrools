package com.biit.drools.form.serialization;

import com.biit.drools.form.DroolsSubmittedCategory;
import com.biit.form.submitted.serialization.jackson.SubmittedObjectSerializer;
import com.fasterxml.jackson.core.JsonGenerator;

import java.io.IOException;
import java.io.Serial;

public class DroolsSubmittedCategorySerializer extends SubmittedObjectSerializer<DroolsSubmittedCategory> {

    @Serial
    private static final long serialVersionUID = 5327704380267106585L;

    @Override
    public void serialize(DroolsSubmittedCategory src, JsonGenerator jgen) throws IOException {
        super.serialize(src, jgen);
    }
}
