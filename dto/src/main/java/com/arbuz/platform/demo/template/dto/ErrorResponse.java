/**
 * Copyright (c) 2016. Arbuz All rights reserved.  http://www.arbuz.io.
 */

package com.arbuz.platform.demo.template.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.hateoas.ResourceSupport;

import java.io.Serializable;

@JsonPropertyOrder({ "code", "message", "developerMessage", "type", "links" })
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ErrorResponse extends ResourceSupport implements Serializable
{
    @JsonProperty(value = "code", required = true)
    private String code;
    @JsonProperty(value = "message", required = true)
    private String message;
    @JsonProperty(value = "developerMessage", required = true)
    private String developerMessage;
    @JsonProperty(value = "type", required = true)
    private String type;

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getDeveloperMessage()
    {
        return developerMessage;
    }

    public void setDeveloperMessage(String developerMessage)
    {
        this.developerMessage = developerMessage;
    }

    @Override
    public String toString()
    {
        return "ErrorResponse{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", developerMessage='" + developerMessage + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
