package com.biit.drools.form.serialization;

import com.biit.drools.form.DroolsSubmittedCategory;
import com.biit.form.submitted.serialization.jackson.SubmittedObjectSerializer;
import com.fasterxml.jackson.core.JsonGenerator;

import java.io.IOException;

public class DroolsSubmittedCategorySerializer extends SubmittedObjectSerializer<DroolsSubmittedCategory> {

    @Override
    public void serialize(DroolsSubmittedCategory src, JsonGenerator jgen) throws IOException {
        super.serialize(src, jgen);
    }
}
