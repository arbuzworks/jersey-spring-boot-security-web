/**
 * Copyright (c) 2016. Arbuz All rights reserved.  http://www.arbuz.io.
 */

package com.arbuz.platform.demo.template.test;

import com.arbuz.platform.demo.template.dto.AuthorizationRequest;
import com.arbuz.platform.demo.template.dto.AuthorizationResponse;
import com.arbuz.platform.demo.template.dto.Check;
import com.arbuz.platform.demo.template.dto.CreditCard;
import com.arbuz.platform.demo.template.dto.RetrieveTransactionsResponse;
import com.arbuz.platform.demo.template.dto.UpdateTransactionRequest;
import com.arbuz.platform.demo.template.model.Transaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(TransactionTest.class)
@Suite.SuiteClasses({ TransactionTest.CrudTest.class })
public class TransactionTest extends Suite
{
    private static final Logger logger = LoggerFactory.getLogger(TransactionTest.class);
    private static String transactionId = null;

    public TransactionTest(Class<?> klass, RunnerBuilder builder) throws InitializationError
    {
        super(klass, builder);
    }

    @FixMethodOrder(MethodSorters.JVM)
    public static class CrudTest extends PaymentResourceTest
    {
        private AuthorizationRequest authorizationRequest;
        private UpdateTransactionRequest updateTransactionRequest;

        @Before
        public void createAuthorizationRequest()
        {
            authorizationRequest = new AuthorizationRequest();
            authorizationRequest.setCustomer("admin");

            CreditCard creditCard = new CreditCard();
            creditCard.setNumber("5454545454545454");
            creditCard.setCvv("123");
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
            check.setCheckNumber("123");

            updateTransactionRequest.setCheck(check);
            updateTransactionRequest.setAmount(11.5);

            updateTransactionRequest.setStatus("AUTHORIZED");
        }

        @Test
        public void testAuthorize()
        {
            logger.debug("AuthorizationRequest: " + authorizationRequest);

            Response response = target("/payment").request().post(Entity.entity(authorizationRequest, MediaType.APPLICATION_JSON_TYPE));

            logger.debug("Authorize response: " + response);

            if (response.getStatus() == HttpStatus.OK.value())
            {
                transactionId = response.readEntity(AuthorizationResponse.class).getTransactionId();

                logger.debug("transactionId: " + transactionId);

                assertNotNull(transactionId);
            }
            else
            {
                Assert.fail();
            }
        }

        @Test
        public void testRetrievePosts()
        {
            Response response = target("/payment").path("transactions").queryParam("status", "PENDING").request().get();

            logger.debug("Transactions response: " + response);

            List<Transaction> transactions = null;

            if (response.getStatus() == HttpStatus.OK.value())
            {
                transactions = response.readEntity(RetrieveTransactionsResponse.class).getTransactions();
            }

            logger.debug("Transactions: " + transactions);

            assertTrue(transactions != null && transactions.size() > 0);
        }

        @Test
        public void testUpdateTransaction()
        {
            if (transactionId != null)
            {
                Response response = target("/payment").path(transactionId).request().put(Entity.entity(updateTransactionRequest, MediaType.APPLICATION_JSON_TYPE));

                logger.debug("Update response: " + response);

                assertTrue(response.getStatus() == HttpStatus.OK.value());
            }
            else
            {
                Assert.fail();
            }
        }

        @Test
        public void testDeleteTransaction()
        {
            if (transactionId != null)
            {
                Response response = target("/payment").path(transactionId).request().delete();

                logger.debug("Delete response: " + response);

                assertTrue(response.getStatus() == HttpStatus.OK.value());
            }
            else
            {
                Assert.fail();
            }
        }

        @After
        public void destroyAuthorizationRequest()
        {
            authorizationRequest = null;
            assertNull(authorizationRequest);
        }

        @After
        public void destroyUpdateTransactionRequest()
        {
            updateTransactionRequest = null;
            assertNull(updateTransactionRequest);
        }

    }
}
