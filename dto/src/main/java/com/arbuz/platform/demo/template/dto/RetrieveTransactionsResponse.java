/**
 * Copyright (c) 2016. Arbuz All rights reserved.  http://www.arbuz.io.
 */

package com.arbuz.platform.demo.template.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.arbuz.platform.demo.template.model.Transaction;
import org.springframework.hateoas.ResourceSupport;

import java.io.Serializable;
import java.util.List;

@JsonPropertyOrder({ "transactions", "links" })
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RetrieveTransactionsResponse extends ResourceSupport implements Serializable
{
    @JsonProperty(value = "transactions", required = true)
    private List<Transaction> transactions;

    public List<Transaction> getTransactions()
    {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions)
    {
        this.transactions = transactions;
    }

    @Override
    public String toString()
    {
        return "RetrieveTransactionsResponse{" +
                "transactions=" + transactions +
                '}';
    }
}
