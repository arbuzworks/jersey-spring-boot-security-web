/**
 * Copyright (c) 2016. Arbuz All rights reserved.  http://www.arbuz.io.
 */

package com.arbuz.platform.demo.template.dao.test;

import com.arbuz.platform.demo.template.dao.PaymentDao;
import com.arbuz.platform.demo.template.model.Transaction;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PaymentDaoTest
{
    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private PaymentDao paymentDao;

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateNewTransaction()
    {
        when(jdbcTemplate.update(anyString(), anyString(), anyString(), anyDouble(),
                                 anyString(), anyString(), anyString(),
                                 anyString(), anyString(), anyString(), anyString())).thenReturn(1);
        paymentDao.createNewPayment(RandomStringUtils.randomAlphanumeric(32).toUpperCase(), "admin", 10.00,
                                    "1234123412341234", "123", "12/17", "12345678", "123456", "123", "PENDING");

        verify(jdbcTemplate).update(anyString(), anyString(), anyString(), anyDouble(),
                anyString(), anyString(), anyString(),
                anyString(), anyString(), anyString(), anyString());
    }

    @Test
    public void testGetAllTransactions()
    {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction());

        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(transactions);
        assertNotNull(paymentDao.getAllTransactions());

        verify(jdbcTemplate).query(anyString(), any(RowMapper.class));
    }

    @Test
    public void testFindTransactionsByStatus()
    {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction());

        when(jdbcTemplate.query(anyString(), any(Object[].class), any(RowMapper.class))).thenReturn(transactions);
        assertNotNull(paymentDao.findTransactions("PENDING"));

        verify(jdbcTemplate).query(anyString(), any(Object[].class), any(RowMapper.class));
    }

    @Test
    public void updateTransaction()
    {
        when(jdbcTemplate.update(anyString(), anyString(), anyDouble(), anyString(), anyString(), anyString(), anyString(),
                                 anyString(), anyString(), anyString(), anyString())).thenReturn(1);
        assertTrue(paymentDao.updateTransaction("admin", 20.00, null, null, null, "123456", "1234567890", "123", "AUTHORIZED",
                                                 RandomStringUtils.randomAlphanumeric(32).toUpperCase()));

        verify(jdbcTemplate).update(anyString(), anyString(), anyDouble(), anyString(), anyString(), anyString(), anyString(),
                                    anyString(), anyString(), anyString(), anyString());
    }

    @Test
    public void testTransaction()
    {
        when(jdbcTemplate.update(anyString(), anyString())).thenReturn(1);
        assertTrue(paymentDao.deleteTransaction(RandomStringUtils.randomAlphanumeric(32).toUpperCase()));

        verify(jdbcTemplate).update(anyString(), anyString());
    }
}