package com.zzg.config;

import org.quartz.Job;

/**
 */
public class QuartzJob {

    private String jobName;
    private String jobGroup;
    private String jobTrigger;
    private Object jobExtendData;
    private Class<? extends Job> jobClass;
    private String cronExpression;

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getJobTrigger() {
        return jobTrigger;
    }

    public void setJobTrigger(String jobTrigger) {
        this.jobTrigger = jobTrigger;
    }

    public Object getJobExtendData() {
        return jobExtendData;
    }

    public void setJobExtendData(Object jobExtendData) {
        this.jobExtendData = jobExtendData;
    }

    public Class<? extends Job> getJobClass() {
        return jobClass;
    }

    @SuppressWarnings("unchecked")
    public void setJobClass(String jobClass) {
        Class<? extends Job> clazz = null;
        try {
            clazz = (Class<? extends Job>) Class.forName(jobClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        this.jobClass = clazz;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

}
