/**
 * Copyright (c) 2016. Arbuz All rights reserved.  http://www.arbuz.io.
 */

package com.arbuz.platform.demo.template.test;

import com.arbuz.platform.demo.template.resource.PaymentResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.SpringLifecycleListener;
import org.glassfish.jersey.test.JerseyTest;
import org.springframework.web.filter.RequestContextFilter;

import javax.ws.rs.core.Application;

public class PaymentResourceTest extends JerseyTest
{
    @Override
    protected Application configure()
    {
        ResourceConfig resourceConfig = new ResourceConfig(PaymentResource.class);

        resourceConfig.register(SpringLifecycleListener.class);
        resourceConfig.register(RequestContextFilter.class);

        resourceConfig.property("contextConfigLocation", "classpath:api-context.xml");
        return resourceConfig;
    }
}
