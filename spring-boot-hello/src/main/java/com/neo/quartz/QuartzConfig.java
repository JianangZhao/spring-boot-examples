package com.neo.quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail jobDetail(){
        return JobBuilder.newJob(TaskJob.class)
                .withIdentity("taskJob","taskGroup")
                .withDescription("task description: welcome")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger trigger(){
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail())
                .withIdentity("taskJob","taskGroup")
                /**
                 * CronScheduleBuilder.cronSchedule("0 10 15 * * WED") 中的 cron 表达式 "0 10 15 * * WED" 表示：
                 * 0 表示秒（这里设置为 0 秒）。
                 * 10 表示分钟（这里设置为 10 分钟）。
                 * 15 表示小时（这里设置为 15 小时，即下午三点）。
                 * * 表示日期（这里设置为每一天）。
                 * * 表示月份（这里设置为每个月）。
                 * WED 表示星期几（这里设置为周三）
                 */
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0/30 * * * ?"))
                .build();
    }
}
