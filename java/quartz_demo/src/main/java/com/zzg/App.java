package com.zzg;

import com.zzg.controller.QuartzController;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.Resource;

@SpringBootApplication
@EnableAsync
public class App  implements ApplicationRunner, ApplicationListener<ContextClosedEvent> {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }


    @Resource
    private QuartzController quartzController;
    @Resource
    private Scheduler scheduler;

    @Override
    public void run(ApplicationArguments args) {

        // 初始化这个定时任务（jobName）
        quartzController.initJob("TestJob1");

    }

    @Override
    public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {
        try {
            scheduler.shutdown(true);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }



}
