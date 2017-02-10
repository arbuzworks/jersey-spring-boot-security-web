/**
 * Copyright (c) 2016. Arbuz All rights reserved.  http://www.arbuz.io.
 */

package com.arbuz.platform.demo.template.service;

import com.arbuz.platform.demo.template.dao.PaymentDao;
import com.arbuz.platform.demo.template.dto.AuthorizationRequest;
import com.arbuz.platform.demo.template.dto.ErrorCode;
import com.arbuz.platform.demo.template.dto.Check;
import com.arbuz.platform.demo.template.dto.CreditCard;
import com.arbuz.platform.demo.template.dto.Status;
import com.arbuz.platform.demo.template.dto.UpdateTransactionRequest;
import com.arbuz.platform.demo.template.model.Transaction;
import com.arbuz.platform.demo.template.service.exception.PaymentException;
import com.arbuz.platform.demo.template.service.helpers.ErrorHandler;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

@Component("paymentService")
public class PaymentService
{
    private final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    @Autowired
    private PaymentDao paymentDao;
    @Autowired
    private ErrorHandler errorHandler;

    public String authorize(AuthorizationRequest request, Locale locale) throws PaymentException
    {
        String transactionId = RandomStringUtils.randomAlphanumeric(32).toUpperCase();

        logger.debug("Transaction Id: " + transactionId);

        try
        {
            CreditCard creditCard = request.getCreditCard();

            if (creditCard != null)
            {
                paymentDao.createNewPayment(transactionId, request.getCustomer(), request.getAmount(),
                        creditCard.getNumber(),
                        creditCard.getExpirationDate(),
                        creditCard.getCvv(),
                        null,
                        null,
                        null,
                        Status.PENDING.name());
            }
            else
            {
                Check check = request.getCheck();

                paymentDao.createNewPayment(transactionId, request.getCustomer(), request.getAmount(),
                        null,
                        null,
                        null,
                        check.getRoutingNumber(),
                        check.getAccountNumber(),
                        check.getCheckNumber(),
                        Status.PENDING.name());
            }

            return transactionId;
        }
        catch (DataAccessException dae)
        {
            throw errorHandler.handleError(ErrorCode.AUTHORIZATION_FAILED, dae, locale);
        }
    }

    public List<Transaction> retrieveTransactions(String status, Locale locale) throws PaymentException
    {
        try
        {
            if (status == null)
            {
                return paymentDao.getAllTransactions();
            }
            else
            {
                return paymentDao.findTransactions(status);
            }
        }
        catch (DataAccessException dae)
        {
            throw errorHandler.handleError(ErrorCode.RETRIEVE_TRANSACTIONS_FAILED, dae, locale);
        }
    }

    public boolean removeTransaction(String transactionId, Locale locale) throws PaymentException
    {
        try
        {
            return paymentDao.deleteTransaction(transactionId);
        }
        catch (DataAccessException dae)
        {
            throw errorHandler.handleError(ErrorCode.DELETE_TRANSACTION_FAILED, dae, locale);
        }
    }

    public boolean updateTransaction(String transactionId, UpdateTransactionRequest request, Locale locale) throws PaymentException
    {
        try
        {
            if (request.getCreditCard() != null)
            {
                return paymentDao.updateTransaction(
                        request.getCustomer(),
                        request.getAmount(),
                        request.getCreditCard().getNumber(),
                        request.getCreditCard().getCvv(),
                        request.getCreditCard().getExpirationDate(),
                        null,
                        null,
                        null,
                        request.getStatus(),
                        transactionId);
            }
            else
            {
                return paymentDao.updateTransaction(
                        request.getCustomer(),
                        request.getAmount(),
                        null,
                        null,
                        null,
                        request.getCheck().getRoutingNumber(),
                        request.getCheck().getAccountNumber(),
                        request.getCheck().getCheckNumber(),
                        request.getStatus(),
                        transactionId);
            }
        }
        catch (DataAccessException dae)
        {
            throw errorHandler.handleError(ErrorCode.UPDATE_TRANSACTION_FAILED, dae, locale);
        }
    }
}
