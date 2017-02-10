/**
 * Copyright (c) 2016. Arbuz All rights reserved.  http://www.arbuz.io.
 */

package com.arbuz.platform.demo.template.resource;

import javax.ws.rs.core.Variant;
import java.util.List;
import java.util.Locale;

public interface ApiConstants
{
    List<Variant> LANGUAGE_VARIANTS = Variant.languages(Locale.US, Locale.FRENCH).add().build();
}
