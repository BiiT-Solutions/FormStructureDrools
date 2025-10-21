package com.biit.drools.form.serialization;

/*-
 * #%L
 * Form Structure Drools Engine
 * %%
 * Copyright (C) 2015 - 2025 BiiT Sourcing Solutions S.L.
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * #L%
 */

import com.biit.drools.form.DroolsSubmittedQuestion;
import com.biit.form.submitted.serialization.jackson.SubmittedObjectSerializer;
import com.fasterxml.jackson.core.JsonGenerator;

import java.io.IOException;
import java.io.Serial;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DroolsSubmittedQuestionSerializer extends SubmittedObjectSerializer<DroolsSubmittedQuestion> {

    @Serial
    private static final long serialVersionUID = 1642862259844422396L;

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
