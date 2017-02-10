/**
 * Copyright (c) 2016. Arbuz All rights reserved.  http://www.arbuz.io.
 */

package com.arbuz.platform.demo.template.dto.map;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.arbuz.platform.demo.template.dto.ErrorCode;

import java.io.IOException;

public class ErrorCodeSerializer extends JsonSerializer<ErrorCode>
{
    @Override
    public void serialize(ErrorCode value, JsonGenerator generator,
                          SerializerProvider provider) throws IOException
    {
        generator.writeStartObject();
        generator.writeFieldName("code");
        generator.writeNumber(value.code());
        generator.writeEndObject();
    }
}
