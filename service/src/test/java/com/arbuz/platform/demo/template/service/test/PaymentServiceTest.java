/**
 * Copyright (c) 2016. Arbuz All rights reserved.  http://www.arbuz.io.
 */

package com.arbuz.platform.demo.template.service.test;

import com.arbuz.platform.demo.template.dao.PaymentDao;
import com.arbuz.platform.demo.template.dto.AuthorizationRequest;
import com.arbuz.platform.demo.template.dto.Check;
import com.arbuz.platform.demo.template.dto.CreditCard;
import com.arbuz.platform.demo.template.dto.UpdateTransactionRequest;
import com.arbuz.platform.demo.template.service.PaymentService;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.Locale;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.spy;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PaymentService.class)
public class PaymentServiceTest
{
    @Mock
    private PaymentDao paymentDao;

    @InjectMocks
    private PaymentService paymentService;

    private AuthorizationRequest authorizationRequest;

    private UpdateTransactionRequest updateTransactionRequest;

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);
        paymentService = spy(paymentService);
    }

    @Before
    public void createAuthorizationRequest()
    {
        authorizationRequest = new AuthorizationRequest();
        authorizationRequest.setCustomer("admin");

        CreditCard creditCard = new CreditCard();
        creditCard.setNumber("5454545454545454");
        creditCard.setCvv("125");
        creditCard.setExpirationDate("1216");

        authorizationRequest.setCreditCard(creditCard);
        authorizationRequest.setAmount(10.00);
    }

    @Before
    public void createUpdateTransactionRequest()
    {
        updateTransactionRequest = new UpdateTransactionRequest();
        updateTransactionRequest.setCustomer("consumer");

        updateTransactionRequest.setCreditCard(null);

        Check check = new Check();
        check.setRoutingNumber("123456");
        check.setAccountNumber("1234567890");
        check.setCheckNumber("124");

        updateTransactionRequest.setCheck(check);
        updateTransactionRequest.setAmount(11.5);

        updateTransactionRequest.setStatus("AUTHORIZED");
    }

    @Test
    public void testAuthorize() throws Exception
    {
        doNothing().when(paymentDao).createNewPayment(anyString(), anyString(), anyDouble(), anyString(), anyString(),
                anyString(), anyString(), anyString(), anyString(), anyString());

        assertNotNull(paymentService.authorize(authorizationRequest, Locale.US));

        verify(paymentDao).createNewPayment(anyString(), anyString(), anyDouble(), anyString(), anyString(),
                anyString(), anyString(), anyString(), anyString(), anyString());
    }

    @Test
    public void testRetrieveTransactions() throws Exception
    {
        doReturn(new ArrayList<>()).when(paymentDao).findTransactions(anyString());

        assertNotNull(paymentService.retrieveTransactions("PENDING", Locale.US));

        verify(paymentDao).findTransactions(anyString());
    }

    @Test
    public void testUpdateTransaction() throws Exception
    {
        doReturn(true).when(paymentDao).updateTransaction(anyString(), anyDouble(), anyString(), anyString(), anyString(),
                                                          anyString(), anyString(), anyString(), anyString(), anyString());

        assertNotNull(paymentService.updateTransaction(RandomStringUtils.randomAlphanumeric(32).toUpperCase(),
                                                        updateTransactionRequest, Locale.US));

        verify(paymentDao).updateTransaction(anyString(), anyDouble(), anyString(), anyString(), anyString(),
                anyString(), anyString(), anyString(), anyString(), anyString());
    }

    @Test
    public void testRemoveTransaction() throws Exception
    {
        doReturn(true).when(paymentDao).deleteTransaction(anyString());

        assertTrue(paymentService.removeTransaction(RandomStringUtils.randomAlphanumeric(32).toUpperCase(), Locale.US));

        verify(paymentDao).deleteTransaction(anyString());
    }
}
