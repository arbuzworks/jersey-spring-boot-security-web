/**
 * Copyright (c) 2016. Arbuz All rights reserved.  http://www.arbuz.io.
 */

package com.arbuz.platform.demo.template.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.hateoas.ResourceSupport;

import java.io.Serializable;

@JsonPropertyOrder({ "transactionId", "links" })
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AuthorizationResponse extends ResourceSupport implements Serializable
{
    private String transactionId;

    @JsonProperty(value = "transactionId")
    public String getTransactionId()
    {
        return transactionId;
    }

    @JsonProperty(value = "transactionId")
    public void setTransactionId(String transactionId)
    {
        this.transactionId = transactionId;
    }

    @Override
    public String toString()
    {
        return "AuthorizationResponse{" +
                "transactionId='" + transactionId + '\'' +
                '}';
    }
}
