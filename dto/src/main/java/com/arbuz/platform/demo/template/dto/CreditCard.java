/**
 * Copyright (c) 2016. Arbuz All rights reserved.  http://www.arbuz.io.
 */

package com.arbuz.platform.demo.template.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@JsonPropertyOrder({ "number", "cvv", "expirationDate" })
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CreditCard implements Serializable
{
    @NotNull
    @JsonProperty(value = "number", required = true)
    private String number;

    @NotNull
    @JsonProperty(value = "cvv", required = true)
    private String cvv;

    @NotNull
    @JsonProperty(value = "expirationDate", required = true)
    private String expirationDate;

    public String getNumber()
    {
        return number;
    }

    public void setNumber(String number)
    {
        this.number = number;
    }

    public String getCvv()
    {
        return cvv;
    }

    public void setCvv(String cvv)
    {
        this.cvv = cvv;
    }

    public String getExpirationDate()
    {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate)
    {
        this.expirationDate = expirationDate;
    }

    @Override
    public String toString()
    {
        return "CreditCard{" +
                "number='" + number + '\'' +
                ", cvv='" + cvv + '\'' +
                ", expirationDate='" + expirationDate + '\'' +
                '}';
    }
}
