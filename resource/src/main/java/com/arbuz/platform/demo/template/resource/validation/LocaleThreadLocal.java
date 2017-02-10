/**
 * Copyright (c) 2016. Arbuz All rights reserved.  http://www.arbuz.io.
 */

package com.arbuz.platform.demo.template.resource.validation;

import java.util.Locale;

public class LocaleThreadLocal
{
    private static final ThreadLocal<Locale> THREAD_LOCAL = new ThreadLocal<Locale>();

    protected LocaleThreadLocal()
    {
        throw new UnsupportedOperationException();
    }

    public static Locale get()
    {
        return (THREAD_LOCAL.get() == null) ? Locale.getDefault() : THREAD_LOCAL.get();
    }

    public static void set(Locale locale)
    {
        THREAD_LOCAL.set(locale);
    }

    public static void unset()
    {
        THREAD_LOCAL.remove();
    }
}
