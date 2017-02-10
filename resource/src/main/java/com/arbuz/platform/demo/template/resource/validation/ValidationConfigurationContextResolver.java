/**
 * Copyright (c) 2016. Arbuz All rights reserved.  http://www.arbuz.io.
 */

package com.arbuz.platform.demo.template.resource.validation;

import org.glassfish.jersey.server.validation.ValidationConfig;

import javax.validation.ParameterNameProvider;
import javax.validation.Validation;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

@Provider
public class ValidationConfigurationContextResolver implements ContextResolver<ValidationConfig>
{
    @Override
    public ValidationConfig getContext(Class<?> type)
    {
        final ValidationConfig config = new ValidationConfig();

        config.messageInterpolator(new LocaleSpecificMessageInterpolator(Validation.byDefaultProvider().configure()
                .getDefaultMessageInterpolator()));
        config.parameterNameProvider(new CustomParameterNameProvider());

        return config;
    }

    private class CustomParameterNameProvider implements ParameterNameProvider
    {

        private final ParameterNameProvider nameProvider;

        CustomParameterNameProvider()
        {
            nameProvider = Validation.byDefaultProvider().configure().getDefaultParameterNameProvider();
        }

        @Override
        public List<String> getParameterNames(final Constructor<?> constructor)
        {
            return nameProvider.getParameterNames(constructor);
        }

        @Override
        public List<String> getParameterNames(final Method method)
        {
            if ("authorize".equals(method.getName()))
            {
                return Arrays.asList("locale", "authorizationRequest");
            }
            if ("post".equals(method.getName()))
            {
                return Arrays.asList("locale", "transactionId", "postRequest");
            }
            return nameProvider.getParameterNames(method);
        }
    }
}

