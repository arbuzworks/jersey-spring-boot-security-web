/**
 * Copyright (c) 2016. Arbuz All rights reserved.  http://www.arbuz.io.
 */

package com.arbuz.platform.demo.template.resource.test;

import com.arbuz.platform.demo.template.dto.AuthorizationRequest;
import com.arbuz.platform.demo.template.dto.UpdateTransactionRequest;
import com.arbuz.platform.demo.template.resource.PaymentResource;
import com.arbuz.platform.demo.template.service.PaymentService;
import org.apache.commons.lang3.RandomStringUtils;
import org.glassfish.jersey.uri.internal.JerseyUriBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Variant;

import java.util.ArrayList;
import java.util.Locale;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PaymentResourceTest
{
    @InjectMocks
    private PaymentResource paymentResource;

    @Mock
    private Request request;

    @Mock
    private UriInfo uriInfo;

    @Mock
    private PaymentService paymentService;

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);
        when(request.selectVariant(anyList())).thenReturn(new Variant(MediaType.APPLICATION_JSON_TYPE, Locale.US, null));
        when(uriInfo.getBaseUriBuilder()).thenReturn(new JerseyUriBuilder());
    }

    @Test
    public void testAuthorize() throws Exception
    {
        when(paymentService.authorize(any(AuthorizationRequest.class), any(Locale.class))).thenReturn("1");

        when(uriInfo.getAbsolutePathBuilder()).thenReturn(new JerseyUriBuilder());

        assertTrue(paymentResource.authorize(Locale.US, new AuthorizationRequest(), request, uriInfo).getStatus() == Response.Status.OK.getStatusCode());

        verify(request).selectVariant(anyList());
        verify(paymentService).authorize(any(AuthorizationRequest.class), any(Locale.class));

        verify(uriInfo).getAbsolutePathBuilder();
    }

    @Test
    public void testRetrieveTransactions() throws Exception
    {
        when(paymentService.retrieveTransactions(anyString(), any(Locale.class))).thenReturn(new ArrayList());

        assertTrue(paymentResource.retrieveTransactions(Locale.US, "PENDING", request, uriInfo).getStatus() == Response.Status.OK.getStatusCode());

        verify(paymentService).retrieveTransactions(anyString(), any(Locale.class));
        verify(uriInfo).getBaseUriBuilder();
    }

    @Test
    public void testUpdateTransaction() throws Exception
    {
        when(paymentService.updateTransaction(anyString(), any(UpdateTransactionRequest.class), any(Locale.class))).thenReturn(true);

        assertTrue(paymentResource.updateTransaction(Locale.US, RandomStringUtils.randomAlphanumeric(32).toUpperCase(), new UpdateTransactionRequest(), request, uriInfo).getStatus() == Response.Status.OK.getStatusCode());

        verify(paymentService).updateTransaction(anyString(), any(UpdateTransactionRequest.class), any(Locale.class));
    }

    @Test
    public void testRemoveTransaction() throws Exception
    {
        when(paymentService.removeTransaction(anyString(), any(Locale.class))).thenReturn(true);

        assertTrue(paymentResource.removeTransaction(Locale.US, RandomStringUtils.randomAlphanumeric(32).toUpperCase(), request, uriInfo).getStatus() == Response.Status.OK.getStatusCode());

        verify(paymentService).removeTransaction(anyString(), any(Locale.class));
    }

}
