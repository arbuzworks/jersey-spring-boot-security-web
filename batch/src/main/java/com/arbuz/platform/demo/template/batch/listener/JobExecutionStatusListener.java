/**
 * Copyright (c) 2016. Arbuz All rights reserved.  http://www.arbuz.io.
 */

package com.arbuz.platform.demo.template.batch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class JobExecutionStatusListener implements JobExecutionListener
{
    private final Logger logger = LoggerFactory.getLogger(JobExecutionStatusListener.class);

    @Override
    public void beforeJob(JobExecution jobExecution)
    {
        String jobName = jobExecution.getJobParameters().getString("jobName");
        logger.info(jobName + " was started");
    }

    @Override
    public void afterJob(JobExecution jobExecution)
    {
        String jobName = jobExecution.getJobParameters().getString("jobName");
        if (jobExecution.getStatus() == BatchStatus.COMPLETED)
        {
            logger.info(jobName + " was successfully finished");
        }
        else if (jobExecution.getStatus() == BatchStatus.FAILED)
        {
            logger.info(jobName + " was failure finished");
        }
    }
}
