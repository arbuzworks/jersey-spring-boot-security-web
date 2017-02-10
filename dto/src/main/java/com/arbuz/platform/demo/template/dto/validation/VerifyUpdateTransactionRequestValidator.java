/**
 * Copyright (c) 2016. Arbuz All rights reserved.  http://www.arbuz.io.
 */

package com.arbuz.platform.demo.template.dto.validation;

import com.arbuz.platform.demo.template.dto.UpdateTransactionRequest;
import com.arbuz.platform.demo.template.dto.Check;
import com.arbuz.platform.demo.template.dto.CreditCard;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class VerifyUpdateTransactionRequestValidator implements ConstraintValidator<VerifyUpdateTransactionRequest, Object>
{
    @Override
    public void initialize(VerifyUpdateTransactionRequest verifyAutorizationRequest)
    {

    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext)
    {
        UpdateTransactionRequest authorizationRequest = (UpdateTransactionRequest) o;

        CreditCard creditCard = authorizationRequest.getCreditCard();
        Check check = authorizationRequest.getCheck();

        if ((creditCard != null && check == null) || (creditCard == null && check != null))
        {
            return true;
        }

        return false;
    }
}

