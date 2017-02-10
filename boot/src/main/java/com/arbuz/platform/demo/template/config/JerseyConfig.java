/**
 * Copyright (c) 2016. Arbuz All rights reserved.  http://www.arbuz.io.
 */

package com.arbuz.platform.demo.template.config;

import com.arbuz.platform.demo.template.resource.ApiExceptionMapper;
import com.arbuz.platform.demo.template.resource.PaymentResource;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;
import org.glassfish.jersey.server.validation.ValidationFeature;
import org.glassfish.jersey.server.validation.internal.ValidationExceptionMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class JerseyConfig extends ResourceConfig
{
    @Value("${spring.jersey.application-path:/api}")
    private String apiPath;

    public JerseyConfig()
    {
        register(RequestContextFilter.class);
        register(JacksonFeature.class);
        register(ValidationFeature.class);
        register(ApiExceptionMapper.class);
        register(ApplicationConfiguration.class);
        register(ValidationExceptionMapper.class);
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);

        this.registerEndpoints();
    }

    private void registerEndpoints()
    {
        this.register(PaymentResource.class);
    }

    @PostConstruct
    public void init()
    {
        this.configureSwagger();
    }

    private void configureSwagger()
    {
        this.register(ApiListingResource.class);
        this.register(SwaggerSerializers.class);

        BeanConfig config = new BeanConfig();
        config.setTitle("Demo API");
        config.setVersion("v1");
        config.setContact("Arbuz");
        config.setSchemes(new String[]{"http", "https"});
        config.setBasePath(this.apiPath);
        config.setResourcePackage("com.arbuz.platform.demo.template.resource");
        config.setPrettyPrint(true);
        config.setScan(true);
    }
}
