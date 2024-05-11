package com.zzg.config;

import com.alibaba.fastjson.JSONObject;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

/**
 */
@Configuration
@Order(2)
public class QuartzConfig {
    @Bean(name = "schedulerFactory")
    public SchedulerFactory stdSchedulerFactory() {
        return new StdSchedulerFactory();
    }

    @Bean(name = "scheduler")
    public Scheduler scheduler(@Qualifier("schedulerFactory") SchedulerFactory schedulerFactory) throws SchedulerException {
        schedulerFactory.getScheduler().start();
        return schedulerFactory.getScheduler();
    }

    @Bean(name = "quartzManager")
    public QuartzManager quartzManager(@Value("classpath:conf/quartz.jobs.json") Resource quartzJobsResource, @Qualifier("scheduler") Scheduler scheduler) {
        try {
            String json = StreamUtils.copyToString(quartzJobsResource.getInputStream(), StandardCharsets.UTF_8);
            Map<String, QuartzJob> dataExtractJobs = JSONObject.parseArray(json, QuartzJob.class)
                    .stream()
                    .collect(Collectors.toMap(QuartzJob::getJobName, v -> v));
            SchedulerFactory schedulerFactory = new StdSchedulerFactory();
            schedulerFactory.getScheduler().start();
            return new QuartzManager(dataExtractJobs, schedulerFactory.getScheduler());
        } catch (IOException | SchedulerException e) {
            return null;
        }

    }
}
