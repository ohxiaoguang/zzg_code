package com.zzg.config;

import org.quartz.*;
import org.quartz.Trigger.TriggerState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Map;

import static org.quartz.DateBuilder.evenMinuteDate;

/**
 *
 */
public class QuartzManager {

    private static final Logger logger = LoggerFactory.getLogger(QuartzManager.class);

    private Map<String, QuartzJob> jobs;

    private Scheduler scheduler;

    public QuartzManager(Map<String, QuartzJob> jobs, Scheduler scheduler) {
        this.jobs = jobs;
        this.scheduler = scheduler;
    }

    public String listJobs() {
        StringBuilder builder = new StringBuilder();
        if (jobs != null) {
            builder.append(String.format("%-15s%-15s%-15s<br/>", "jobName", "jobGroup", "JobClass"));
            jobs.forEach((k, v) -> {
                builder.append(String.format("%-15s%-15s%-15s<br/>", k, v.getJobGroup(), v.getClass().getName()));
            });
        }
        return builder.toString();
    }

    public String initJob(String key) {
        if (jobs == null) {
            return String.format("任务{s%}不存在", key);
        }
        QuartzJob job = jobs.get(key);
        if (job == null) {
            return String.format("任务{s%}不存在", key);
        }
        return initQuartzJob(job);
    }

    public void initAllJobs() {
        if (jobs != null) {
            jobs.forEach((k, v) -> {
                if (!k.equals("cache.app_rule")) {
                    initQuartzJob(v);
                }
            });
        }
    }

    public void initJobsByGroup(String jobGroup) {
        if (jobs != null) {
            jobs.forEach((k, v) -> {
                if (v.getJobGroup().equals(jobGroup)) {
                    initQuartzJob(v);
                }
            });
        }
    }

    public void runJobsByGroup(String jobGroup) {
        if (jobs != null) {
            jobs.forEach((k, v) -> {
                if (v.getJobGroup().equals(jobGroup)) {
                    runJob(v);
                }
            });
        }
    }

    public String pauseJob(String key) {
        if (jobs == null) {
            return String.format("任务{s%}不存在", key);
        }
        QuartzJob job = jobs.get(key);
        if (job == null) {
            return String.format("任务{s%}不存在", key);
        }
        return pauseJob(job);
    }

    public void resumeJob(String key) {
        if (jobs == null) {
            logger.info("任务{}不存在", key);
            return;
        }
        QuartzJob job = jobs.get(key);
        if (job == null) {
            logger.info("任务{}不存在", key);
            return;
        }
        resumeJob(job);
    }

    public void deleteJob(String key) {
        if (jobs == null) {
            logger.info("任务{}不存在", key);
            return;
        }
        QuartzJob job = jobs.get(key);
        if (job == null) {
            logger.info("任务{}不存在", key);
            return;
        }
        deleteJob(job);
    }

    public String initQuartzJob(QuartzJob job) {
        logger.info("初始化任务调度: {}({})", job.getJobName(), job.getJobGroup());
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
            Trigger trigger = null;
            if ("CronTirgger".equals(job.getJobTrigger())) {
                trigger = TriggerBuilder.newTrigger()
                        .withIdentity(triggerKey)
                        .withSchedule(CronScheduleBuilder.cronSchedule(job.getCronExpression())).build();
            } else {
                trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).startAt(evenMinuteDate(new Date())).build();
            }
            if (null != trigger) {
                return addQuartzJob(job, trigger);
            } else {
                return "任务触发器配置信息异常！";
            }
        } catch (Exception e) {
            logger.error("初始化任务调度异常！" + e.getMessage(), e);
            return String.format("初始化任务调度异常！%s", e.getMessage());
        }
    }

    /**
     * 向任务调度中添加定时任务
     * @param job 定时任务信息
     * @param trigger 定时调度触发器
     * @author Joyce.Luo
     * @date 2015-3-31 下午04:04:58
     * @version V3.0
     * @since Tomcat6.0, Jdk1.6
     * @copyright Copyright (c) 2015
     */
    private String addQuartzJob(QuartzJob job, Trigger trigger) {
        logger.info("向任务调度中添加定时任务: {}({})", job.getJobName(), job.getJobGroup());
        try {
            JobDetail jobDetail = JobBuilder.newJob(job.getJobClass())
                    .withIdentity(job.getJobName(), job.getJobGroup())
                    .build();
            jobDetail.getJobDataMap().put(job.getClass().getSimpleName(), job);
            scheduler.scheduleJob(jobDetail, trigger);
            return "向任务调度中添加定时任务成功!";
        } catch (Exception e) {
            logger.error("向任务调度中添加定时任务异常！" + e.getMessage(), e);
            return String.format("向任务调度中添加定时任务异常！%s", e.getMessage());
        }
    }

    /**
     * 暂停任务调度中的定时任务
     * @param job 定时任务信息
     * @author Joyce.Luo
     * @date 2015-4-20 下午02:22:53
     * @version V3.0
     * @since Tomcat6.0, Jdk1.6
     * @copyright Copyright (c) 2015
     */
    private String pauseJob(QuartzJob job) {
        logger.info("暂停任务调度中的定时任务: {}({})", job.getJobName(), job.getJobGroup());
        try {
            if (null == job) {
                logger.info("暂停调度任务参数不正常！");
                return "暂停调度任务参数不正常！";
            }
            JobKey jobKey = JobKey.jobKey(job.getJobName(), job.getJobGroup());
            if (null == jobKey) {
                logger.info("任务调度中不存在[" + job.getJobName() + "]定时任务，不予进行暂停！");
                return String.format("任务调度中不存在[%s]定时任务，不予进行暂停！", job.getJobName());
            }
            scheduler.pauseJob(jobKey);
            return String.format("任务[%s]已暂停！", job.getJobName());
        } catch (Exception e) {
            logger.error("暂停任务调度中的定时任务异常！" + e.getMessage(), e);
            return String.format("暂停任务调度中的定时任务异常！%s", e);
        }
    }

    public void runJob(String jobKey) {
        QuartzJob job = jobs.get(jobKey);
        if (job == null) {
            return;
        }
        this.runJob(job);
    }

    /**
     * 立即运行定时任务
     * @param job 定时任务信息
     * @author Joyce.Luo
     * @date 2015-4-20 下午02:08:41
     * @version V3.0
     * @since Tomcat6.0, Jdk1.6
     * @copyright Copyright (c) 2015
     */
    public void runJob(QuartzJob job) {
        if (null == job) {
            logger.info("定时任务信息为空，无法立即运行");
            return;
        }
        logger.info("立即运行任务调度中的定时任务:{}({})", job.getJobName(), job.getJobGroup());
        try {
            JobKey jobKey = JobKey.jobKey(job.getJobName(), job.getJobGroup());
            if (null == jobKey) {
                logger.info("任务调度中不存在[" + job.getJobName() + "]定时任务，不予立即运行！");
                return;
            }
            scheduler.triggerJob(jobKey);
        } catch (Exception e) {
            logger.error("立即运行任务调度中的定时任务异常！" + e.getMessage(), e);
        }
    }

    /**
     * 修改任务调度中的定时任务
     * @param job 定时任务信息
     * @param triggerKey 定时调度触发键
     * @param trigger 定时调度触发器
     * @author Joyce.Luo
     * @date 2015-3-31 下午04:16:54
     * @version V3.0
     * @since Tomcat6.0, Jdk1.6
     * @copyright Copyright (c) 2015
     */
    void updateQuartzJob(QuartzJob job, TriggerKey triggerKey, CronTrigger trigger) {
        logger.info("修改任务调度中的定时任务");
        try {
            if (null == job || null == triggerKey || null == trigger) {
                logger.info("修改调度任务参数不正常！");
                return;
            }
            logger.info("原始任务表达式:" + trigger.getCronExpression()
                    + "，现在任务表达式:" + job.getCronExpression());
            if (trigger.getCronExpression().equals(job.getCronExpression())) {
                logger.info("任务调度表达式一致，不予进行修改！");
                return;
            }
            logger.info("任务调度表达式不一致，进行修改");
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (Exception e) {
            logger.error("修改任务调度中的定时任务异常！" + e.getMessage(), e);
        }
    }

    /**
     * 恢复任务调度中的定时任务
     * @param job 定时任务信息
     * @author Joyce.Luo
     * @date 2015-4-20 下午02:26:08
     * @version V3.0
     * @since Tomcat6.0, Jdk1.6
     * @copyright Copyright (c) 2015
     */
    void resumeJob(QuartzJob job) {
        logger.info("恢复任务调度中的定时任务: {}({})", job.getJobName(), job.getJobGroup());
        try {
            if (null == job) {
                logger.info("恢复调度任务参数不正常！");
                return;
            }
            JobKey jobKey = JobKey.jobKey(job.getJobName(), job.getJobGroup());
            if (null == jobKey) {
                logger.info("任务调度中不存在[" + job.getJobName() + "]定时任务，不予进行恢复！");
                return;
            }
            scheduler.resumeJob(jobKey);
        } catch (Exception e) {
            logger.error("恢复任务调度中的定时任务异常！" + e.getMessage(), e);
        }
    }

    /**
     * 删除任务调度中的定时任务
     * @param job 定时任务信息
     * @date 2015-3-31 下午04:30:03
     * @version V3.0
     * @since Tomcat6.0, Jdk1.6
     * @copyright Copyright (c) 2015
     */
    void deleteJob(QuartzJob job) {
        logger.info("删除任务调度中的定时任务");
        try {
            if (null == job) {
                logger.info("删除调度任务参数不正常！");
                return;
            }
            JobKey jobKey = JobKey.jobKey(job.getJobName(), job.getJobGroup());
            if (null == jobKey) {
                logger.info("任务调度中不存在[" + job.getJobName() + "]定时任务，不予进行删除！");
                return;
            }
            scheduler.deleteJob(jobKey);
        } catch (Exception e) {
            logger.error("删除任务调度中的定时任务异常！" + e.getMessage(), e);
        }
    }

    /**
     * 删除任务调度定时器
     * @param triggerKey
     * @date 2015-3-31 下午04:35:33
     * @version V3.0
     * @since Tomcat6.0, Jdk1.6
     * @copyright Copyright (c) 2015
     */
    void deleteJob(TriggerKey triggerKey) {
        logger.info("删除任务调度定时器");
        try {
            if (null == triggerKey) {
                logger.info("停止任务定时器参数不正常，不予进行停止！");
                return;
            }
            logger.info("停止任务定时器");
            scheduler.pauseTrigger(triggerKey);
            scheduler.unscheduleJob(triggerKey);
        } catch (Exception e) {
            logger.info("删除任务调度定时器异常！" + e.getMessage(), e);
        }
    }

    /**
     * STATE_BLOCKED 4 阻塞
     * STATE_COMPLETE 2 完成
     * STATE_ERROR 3 错误
     * STATE_NONE -1 不存在
     * STATE_NORMAL 0 正常
     * STATE_PAUSED 1 暂停
     */
    String getJobState(QuartzJob job) {

        logger.info("获取任务调度中的任务状态");
        try {
            if (null == job) {
                logger.info("获取任务状态参数不正常！");
                return "";
            }
            TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
            if (null == triggerKey) {
                logger.info("任务调度中不存在[" + job.getJobName() + "]定时任务，无法读取任务状态！");
                return "";
            }
            TriggerState state = scheduler.getTriggerState(triggerKey);
            return state.toString();
        } catch (Exception e) {
            logger.error("恢复任务调度中的定时任务异常！" + e.getMessage(), e);
            return "";
        }
    }
}
