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
