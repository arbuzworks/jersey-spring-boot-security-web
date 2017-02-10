/**
 * Copyright (c) 2016. Arbuz All rights reserved.  http://www.arbuz.io.
 */

package com.arbuz.platform.demo.template.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.arbuz.platform.demo.template.dto.map.ErrorCodeSerializer;

import java.io.Serializable;

@JsonSerialize(using = ErrorCodeSerializer.class)
public enum ErrorCode implements Serializable
{
    AUTHORIZATION_FAILED("600"),
    RETRIEVE_TRANSACTIONS_FAILED("700"),
    DELETE_TRANSACTION_FAILED("800"),
    UPDATE_TRANSACTION_FAILED("900");

    private final String code;

    ErrorCode(String code)
    {
        this.code = code;
    }

    @JsonValue
    public String code()
    {
        return code;
    }

    @JsonCreator
    public static ErrorCode fromValue(String value)
    {
        for (ErrorCode errorCode : ErrorCode.values())
        {
            if (errorCode.code.equals(value))
            {
                return errorCode;
            }
        }

        throw new IllegalArgumentException(value.toString());
    }

    @Override
    public String toString()
    {
        return "ErrorCode{" +
                "code='" + code + '\'' +
                '}';
    }
}
