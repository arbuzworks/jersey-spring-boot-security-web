/**
 * Copyright (c) 2016. Arbuz All rights reserved.  http://www.arbuz.io.
 */

package com.arbuz.platform.demo.template.batch.tasklet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.util.Random;

public class TransactionCounterTasklet implements Tasklet
{
    private final Logger logger = LoggerFactory.getLogger(TransactionCounterTasklet.class);

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception
    {
        logger.info("Current transactions count is: " + (new Random().nextInt()));
        return RepeatStatus.FINISHED;
    }
}
