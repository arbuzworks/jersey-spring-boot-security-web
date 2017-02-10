/**
 * Copyright (c) 2016. Arbuz All rights reserved.  http://www.arbuz.io.
 */

package com.arbuz.platform.demo.template.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Check
{
    @NotNull
    private String routingNumber;

    @NotNull
    private String accountNumber;

    @NotNull
    private String checkNumber;

    @JsonProperty(value = "routingNumber", required = true)
    public String getRoutingNumber()
    {
        return routingNumber;
    }

    @JsonProperty(value = "routingNumber", required = true)
    public void setRoutingNumber(String routingNumber)
    {
        this.routingNumber = routingNumber;
    }

    @JsonProperty(value = "accountNumber", required = true)
    public String getAccountNumber()
    {
        return accountNumber;
    }

    @JsonProperty(value = "accountNumber", required = true)
    public void setAccountNumber(String accountNumber)
    {
        this.accountNumber = accountNumber;
    }

    @JsonProperty(value = "checkNumber", required = true)
    public String getCheckNumber()
    {
        return checkNumber;
    }

    @JsonProperty(value = "checkNumber", required = true)
    public void setCheckNumber(String checkNumber)
    {
        this.checkNumber = checkNumber;
    }

    @Override
    public String toString()
    {
        return "Check{" +
                "routingNumber='" + routingNumber + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", checkNumber='" + checkNumber + '\'' +
                '}';
    }
}
