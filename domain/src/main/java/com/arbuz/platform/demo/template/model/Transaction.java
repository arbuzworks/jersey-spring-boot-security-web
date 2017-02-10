/**
 * Copyright (c) 2016. Arbuz All rights reserved.  http://www.arbuz.io.
 */

package com.arbuz.platform.demo.template.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

@JsonPropertyOrder({ "transactionId", "customer", "amount", "cardNumber", "cvv", "expirationDate", "routingNumber",
        "routingNumber", "accountNumber", "checkNumber", "status" })
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Transaction implements Serializable
{
    @JsonProperty(value = "transactionId", required = true)
    private String transactionId;

    @JsonProperty(value = "customer", required = true)
    private String customer;

    @JsonProperty(value = "amount", required = true)
    private double amount;

    @JsonProperty(value = "cardNumber", required = false)
    private String cardNumber;

    @JsonProperty(value = "cvv", required = false)
    private String cvv;

    @JsonProperty(value = "expirationDate", required = false)
    private String expirationDate;

    @JsonProperty(value = "routingNumber", required = false)
    private String routingNumber;

    @JsonProperty(value = "accountNumber", required = false)
    private String accountNumber;

    @JsonProperty(value = "checkNumber", required = false)
    private String checkNumber;

    @JsonProperty(value = "status", required = false)
    private String status;

    public String getTransactionId()
    {
        return transactionId;
    }

    public void setTransactionId(String transactionId)
    {
        this.transactionId = transactionId;
    }

    public String getCustomer()
    {
        return customer;
    }

    public void setCustomer(String customer)
    {
        this.customer = customer;
    }

    public double getAmount()
    {
        return amount;
    }

    public void setAmount(double amount)
    {
        this.amount = amount;
    }

    public String getCardNumber()
    {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber)
    {
        this.cardNumber = cardNumber;
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

    public String getRoutingNumber()
    {
        return routingNumber;
    }

    public void setRoutingNumber(String routingNumber)
    {
        this.routingNumber = routingNumber;
    }

    public String getAccountNumber()
    {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber)
    {
        this.accountNumber = accountNumber;
    }

    public String getCheckNumber()
    {
        return checkNumber;
    }

    public void setCheckNumber(String checkNumber)
    {
        this.checkNumber = checkNumber;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    @Override
    public String toString()
    {
        return "Transaction{" +
                "transactionId='" + transactionId + '\'' +
                ", customer='" + customer + '\'' +
                ", amount=" + amount +
                ", cardNumber='" + cardNumber + '\'' +
                ", cvv='" + cvv + '\'' +
                ", expirationDate='" + expirationDate + '\'' +
                ", routingNumber='" + routingNumber + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", checkNumber='" + checkNumber + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
