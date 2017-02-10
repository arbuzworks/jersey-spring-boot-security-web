/**
 * Copyright (c) 2016. Arbuz All rights reserved.  http://www.arbuz.io.
 */

package com.arbuz.platform.demo.template.resource.validation;

import javax.validation.MessageInterpolator;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LocaleSpecificMessageInterpolator implements MessageInterpolator
{

    private static final Logger logger = Logger.getLogger(LocaleSpecificMessageInterpolator.class.getName());

    private final MessageInterpolator defaultInterpolator;

    public LocaleSpecificMessageInterpolator(MessageInterpolator interpolator)
    {
        this.defaultInterpolator = interpolator;
    }

    @Override
    public String interpolate(String message, Context context)
    {
        logger.log(Level.CONFIG, "Selecting the language " + LocaleThreadLocal.get() + " for the error message.");
        return defaultInterpolator.interpolate(message, context, LocaleThreadLocal.get());
    }

    @Override
    public String interpolate(String message, Context context, Locale locale)
    {
        return defaultInterpolator.interpolate(message, context, locale);
    }
}
