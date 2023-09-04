package com.biit.drools.form.serialization;

import com.biit.drools.form.DroolsSubmittedQuestion;
import com.biit.form.submitted.serialization.jackson.SubmittedObjectSerializer;
import com.fasterxml.jackson.core.JsonGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DroolsSubmittedQuestionSerializer extends SubmittedObjectSerializer<DroolsSubmittedQuestion> {

    @Override
    public void serialize(DroolsSubmittedQuestion src, JsonGenerator jgen) throws IOException {
        super.serialize(src, jgen);

        jgen.writeFieldName("answers");
        jgen.writeStartArray("answers");
        final List<String> answers = new ArrayList<>(src.getAnswers());
        Collections.sort(answers);
        for (String value : answers) {
            jgen.writeString(value);
        }
        jgen.writeEndArray();
    }
}
