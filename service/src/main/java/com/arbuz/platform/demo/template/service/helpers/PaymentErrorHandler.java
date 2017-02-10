/**
 * Copyright (c) 2016. Arbuz All rights reserved.  http://www.arbuz.io.
 */

package com.arbuz.platform.demo.template.service.helpers;

import com.arbuz.platform.demo.template.dto.ErrorCode;
import com.arbuz.platform.demo.template.service.exception.PaymentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class PaymentErrorHandler implements ErrorHandler
{
    private final Logger logger = LoggerFactory.getLogger(PaymentErrorHandler.class);

    @Autowired
    private MessageSource messageSource;

    @Override
    public PaymentException handleError(ErrorCode errorCode, Locale locale)
    {
        return this.handleError(errorCode, (Object[]) null, locale);
    }

    @Override
    public PaymentException handleError(ErrorCode errorCode, Object[] objects, Locale locale)
    {
        String code = errorCode.code();
        String message = messageSource.getMessage(code, objects, locale);

        logger.error("code: " + code + " " + "message: " + message);

        return new PaymentException(code, message);
    }

    @Override
    public PaymentException handleError(ErrorCode errorCode, Throwable cause, Locale locale)
    {
        return this.handleError(errorCode, cause, null, locale);
    }

    @Override
    public PaymentException handleError(ErrorCode errorCode, Throwable cause, Object[] objects, Locale locale)
    {
        String code = errorCode.code();
        String message = messageSource.getMessage(code, objects, locale);

        logger.error("code: " + code + " " + "message: " + message);

        return new PaymentException(code, message, cause);
    }

    @Override
    public PaymentException handleError(ErrorCode errorCode, String message, Locale locale)
    {
        return this.handleError(errorCode, new Throwable(message),  locale);
    }

    @Override
    public PaymentException handleError(ErrorCode errorCode, String message, Object[] objects, Locale locale)
    {
        return this.handleError(errorCode, new Throwable(message), objects, locale);
    }
}
