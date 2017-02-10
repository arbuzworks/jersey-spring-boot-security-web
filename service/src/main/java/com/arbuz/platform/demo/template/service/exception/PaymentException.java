/**
 * Copyright (c) 2016. Arbuz All rights reserved.  http://www.arbuz.io.
 */

package com.arbuz.platform.demo.template.service.exception;

public class PaymentException extends Exception
{
    private String code;

    public PaymentException(String code, String message)
    {
        super(message);
        this.code = code;
        initCause(new Throwable(message));
    }

    public PaymentException(String code, String message, Throwable cause)
    {
        super(message, cause);
        this.code = code;
    }

    public String getCode()
    {
        return code;
    }
}

