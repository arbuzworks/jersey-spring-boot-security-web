/**
 * Copyright (c) 2016. Arbuz All rights reserved.  http://www.arbuz.io.
 */

package com.arbuz.platform.demo.template.dao;

import com.arbuz.platform.demo.template.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository("paymentDao")
public class PaymentDao
{
    private final Logger logger = LoggerFactory.getLogger(PaymentDao.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createNewPayment(String transactionId, String customer, double amount,
                                 String cardNumber, String cvv, String expirationDate,
                                 String routingNumber, String accountNumber, String checkNumber, String status) throws DataAccessException
    {
        if (jdbcTemplate.update("insert into TRANSACTION (transaction_id, customer, amount, card_number, cvv, expiration_date, " +
                                                        "routing_number, account_number, check_number, status) " +
                                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                transactionId,
                customer,
                amount,
                cardNumber,
                cvv,
                expirationDate,
                routingNumber,
                accountNumber,
                checkNumber,
                status
                ) != 1)
        {
            logger.error("Transaction was not created");
            throw new RecoverableDataAccessException("Transaction was not created");
        }
    }

    public List<Transaction> getAllTransactions() throws DataAccessException
    {
        return jdbcTemplate.query("select transaction_id, customer, amount, card_number, cvv, expiration_date, " +
                "routing_number, account_number, check_number, status from TRANSACTION",
                new TransactionMapper());
    }

    public List<Transaction> findTransactions(String status) throws DataAccessException
    {
        return jdbcTemplate.query("select transaction_id, customer, amount, card_number, cvv, expiration_date, routing_number, account_number, check_number, status from TRANSACTION where status = ?",
                new Object[] {status},
                new TransactionMapper());
    }

    public boolean updateTransaction(String customer, double amount, String cardNumber, String cvv, String expirationDate,
                                     String routingNumber, String accountNumber, String checkNumber, String status,
                                     String transactionId) throws DataAccessException
    {
        if (jdbcTemplate.update("update TRANSACTION set customer = ?, amount = ?, card_number = ?, cvv = ?, expiration_date = ?,  " +
                                "routing_number = ?, account_number = ?, check_number = ?, status = ? " +
                                "where transaction_id = ?",
                                customer,
                                amount,
                                cardNumber,
                                cvv,
                                expirationDate,
                                routingNumber,
                                accountNumber,
                                checkNumber,
                                status,
                                transactionId) != 1)
        {
            logger.error("Transaction does not exist");
            return false;
        }

        return true;
    }

    public boolean deleteTransaction(String transactionId) throws DataAccessException
    {
        if (jdbcTemplate.update("delete from TRANSACTION where transaction_id = ?", transactionId) != 1)
        {
            logger.error("Transaction does not exist");
            return false;
        }

        return true;
    }

    private static final class TransactionMapper implements RowMapper<Transaction>
    {
        public Transaction mapRow(ResultSet rs, int rowNum) throws SQLException
        {
            Transaction transaction = new Transaction();

            transaction.setTransactionId(rs.getString("transaction_id"));
            transaction.setCustomer(rs.getString("customer"));
            transaction.setAmount(rs.getDouble("amount"));
            transaction.setCardNumber(rs.getString("card_number"));
            transaction.setCvv(rs.getString("cvv"));
            transaction.setExpirationDate(rs.getString("expiration_date"));
            transaction.setAccountNumber(rs.getString("account_number"));
            transaction.setCheckNumber(rs.getString("check_number"));
            transaction.setRoutingNumber(rs.getString("routing_number"));
            transaction.setStatus(rs.getString("status"));

            return transaction;
        }
    }
}
