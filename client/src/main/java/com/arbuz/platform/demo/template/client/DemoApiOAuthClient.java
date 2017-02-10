/**
 * Copyright (c) 2016. Arbuz All rights reserved.  http://www.arbuz.io.
 */

package com.arbuz.platform.demo.template.client;

import org.glassfish.jersey.client.oauth1.ConsumerCredentials;
import org.glassfish.jersey.client.oauth1.OAuth1AuthorizationFlow;
import org.glassfish.jersey.client.oauth1.OAuth1ClientSupport;

public class DemoApiOAuthClient
{
    public static void main(String args[])
    {
        ConsumerCredentials consumerCredentials = new ConsumerCredentials("abcdef", "123456");

        //TODO - user proper client builder with real location + any ssl context
        OAuth1AuthorizationFlow authFlow = OAuth1ClientSupport.builder(consumerCredentials)
                .authorizationFlow(
                        "http://localhost:8080/api/oauth1/request_token",
                        "http://localhost:8080/api/oauth1/access_token",
                        "http://localhost:8080/api/oauth1/authorize")
                .build();
        String authorizationUri = authFlow.start();

        System.out.println("Auth URI: " + authorizationUri);
    }

    @SuppressWarnings("unused")
    public void empty()
    {

    }
}
