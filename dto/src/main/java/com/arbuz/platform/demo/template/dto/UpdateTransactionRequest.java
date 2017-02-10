/**
 * Copyright (c) 2016. Arbuz All rights reserved.  http://www.arbuz.io.
 */

package com.arbuz.platform.demo.template.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.arbuz.platform.demo.template.dto.validation.VerifyUpdateTransactionRequest;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@JsonPropertyOrder({ "customer", "creditCard", "check", "amount" })
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@VerifyUpdateTransactionRequest
public class UpdateTransactionRequest implements Serializable
{
    @NotNull
    private String customer;

    @Valid
    private CreditCard creditCard;

    @Valid
    private Check check;

    @NotNull
    private double amount;

    @NotNull
    private String status;

    @JsonProperty(value = "customer", required = true)
    public String getCustomer()
    {
        return customer;
    }

    @JsonProperty(value = "customer", required = true)
    public void setCustomer(String customer)
    {
        this.customer = customer;
    }

    @JsonProperty(value = "creditCard", required = false)
    public CreditCard getCreditCard()
    {
        return creditCard;
    }

    @JsonProperty(value = "creditCard", required = false)
    public void setCreditCard(CreditCard creditCard)
    {
        this.creditCard = creditCard;
    }

    @JsonProperty(value = "check", required = false)
    public Check getCheck()
    {
        return check;
    }

    @JsonProperty(value = "check", required = false)
    public void setCheck(Check check)
    {
        this.check = check;
    }

    @JsonProperty(value = "amount", required = true)
    public double getAmount()
    {
        return amount;
    }

    @JsonProperty(value = "amount", required = true)
    public void setAmount(double amount)
    {
        this.amount = amount;
    }

    @JsonProperty(value = "status", required = true)
    public String getStatus()
    {
        return status;
    }

    @JsonProperty(value = "status", required = true)
    public void setStatus(String status)
    {
        this.status = status;
    }

    @Override
    public String toString()
    {
        return "UpdateTransactionRequest{" +
                "customer='" + customer + '\'' +
                ", creditCard=" + creditCard +
                ", check=" + check +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                '}';
    }
}


