/**
 * Copyright (c) 2016. Arbuz All rights reserved.  http://www.arbuz.io.
 */

package com.arbuz.platform.demo.template.service.helpers;

import com.arbuz.platform.demo.template.dto.ErrorCode;
import com.arbuz.platform.demo.template.service.exception.PaymentException;

import java.util.Locale;

public interface ErrorHandler
{
    PaymentException handleError(ErrorCode errorCode, Locale locale);

    PaymentException handleError(ErrorCode errorCode, Object[] objects, Locale locale);

    PaymentException handleError(ErrorCode errorCode, Throwable cause, Locale locale);

    PaymentException handleError(ErrorCode errorCode, Throwable cause, Object[] objects, Locale locale);

    PaymentException handleError(ErrorCode errorCode, String message, Locale locale);

    PaymentException handleError(ErrorCode errorCode, String message, Object[] objects, Locale locale);
}
