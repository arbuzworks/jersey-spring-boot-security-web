/**
 * Copyright (c) 2016. Arbuz All rights reserved.  http://www.arbuz.io.
 */

package com.arbuz.platform.demo.template.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@PropertySources({
        @PropertySource(value = "classpath:leap-api.yml", ignoreResourceNotFound = true,
                name = "applicationProperties"),
        @PropertySource(value = "file:leap-api.yml", ignoreResourceNotFound = true,
                name = "applicationProperties"),
        @PropertySource(value = "file:./config/leap-api.yml", ignoreResourceNotFound = true,
                name = "applicationProperties"),
        @PropertySource(value = "file:../config/leap-api.yml", ignoreResourceNotFound = true,
                name = "applicationProperties")
})
@EnableScheduling
public class ApplicationConfiguration extends WebMvcConfigurerAdapter
{
    @Autowired
    private Environment environment;

}
