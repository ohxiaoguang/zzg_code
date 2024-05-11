package com.zzg.job;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zzg.bean.TbTest;
import com.zzg.config.QuartzJob;
import com.zzg.config.db.BaseDao;
import com.zzg.config.db.DbPoolConnection;
import com.zzg.mapper.TbTestMapper;
import com.zzg.utils.SpringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class TestTask implements Job {

    static {
//        String a = "1";
//        for (int i = 0; i < 10000; i++) {
//             a += i;
//        }
//        System.out.println(a);

    }

    private static final Logger logger = LoggerFactory.getLogger(TestTask.class);


    @Override
    public void execute(JobExecutionContext context) {
        JobDataMap jdm = context.getJobDetail().getJobDataMap();
        QuartzJob job = (QuartzJob)jdm.get(QuartzJob.class.getSimpleName());
        JSONObject jobExtendData = (JSONObject) job.getJobExtendData();
        String arg1 = jobExtendData.getString("arg1");
        String arg2 = jobExtendData.getString("arg2");
        JSONObject argObj = jobExtendData.getJSONObject("argObj");

        StopWatch watch = StopWatch.createStarted();
        logger.info("任务开始-{}-开始...", arg1);
        try {
            // 执行具体逻辑
            System.out.println("1231123");
            TbTestMapper testMapper = SpringUtils.getBean(TbTestMapper.class);
            List<TbTest> tbTests = testMapper.selectList(Wrappers.lambdaQuery());
            logger.info("testMapper查询结果: {}", JSON.toJSONString(tbTests));

            List<Map<String, Object>> maps = new BaseDao().doSQLQueryToMapList(
                    DbPoolConnection.DbNameEnum.db127, "select * from test");
            logger.info("db127查询结果: {}", JSON.toJSONString(maps));


            logger.info("任务结束-{}-花费时间: {}", arg1, watch.getTime());
        } catch (Exception e) {
            logger.error("定时任务【" + job.getJobName() + "(" + job.getJobGroup() + ")】运行时报错：" + e.getMessage(), e);
        } finally {
            watch.stop();
        }
    }


}
