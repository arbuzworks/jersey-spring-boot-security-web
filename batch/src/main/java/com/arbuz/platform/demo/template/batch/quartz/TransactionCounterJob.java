/**
 * Copyright (c) 2016. Arbuz All rights reserved.  http://www.arbuz.io.
 */

package com.arbuz.platform.demo.template.batch.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class TransactionCounterJob extends QuartzJobBean
{
    private final Logger logger = LoggerFactory.getLogger(TransactionCounterJob.class);
    @Autowired(required = false)
    private JobRegistry jobRegistry;
    @Autowired(required = false)
    private JobLauncher jobLauncher;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException
    {
        try
        {
            Job userCounterJob = jobRegistry.getJob("transactionCounterJob");
            jobLauncher.run(userCounterJob, new JobParametersBuilder()
                    .addString("jobName", "Transaction Counter")
                    .addDate("date", new Date())
                    .toJobParameters());
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
        }
    }

}
