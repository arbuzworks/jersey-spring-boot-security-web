/**
 * Copyright (c) 2016. Arbuz All rights reserved.  http://www.arbuz.io.
 */

package com.arbuz.platform.demo.template.dto.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target({ TYPE })
@Documented
@Constraint(validatedBy = VerifyAuthorizationRequestValidator.class)
public @interface VerifyAuthorizationRequest
{

    String message() default "{authorization.wrong.params}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };

}
