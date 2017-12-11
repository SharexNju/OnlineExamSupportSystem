package top.sharex.oes.core.schedule;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author danielyang
 * @Date 2017/11/21 11:12
 */
@Component
public class OESScheduler {
    private Logger logger = LoggerFactory.getLogger(OESScheduler.class);

    private Scheduler scheduler;

    @PostConstruct
    public void init() {
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
        } catch (SchedulerException e) {
            logger.error("scheduler start error: can not get scheduler instance");
            System.exit(1);
        }

    }

    /**
     * 调度任务
     *
     * @param detail  任务
     * @param trigger 触发器
     */
    public void scheduleJob(JobDetail detail, Trigger trigger) {
        try {
            scheduler.scheduleJob(detail, trigger);
        } catch (SchedulerException e) {
            logger.error("schedule job error for job {} with trigger {}", detail.getKey(), trigger
                    .getKey());
        }
    }

    /**
     * 重新调度任务（更新trigger）
     *
     * @param triggerKey 对应任务的triggerkey
     * @param trigger    更新后的trigger
     */
    public void rescheduleJob(TriggerKey triggerKey, Trigger trigger) {
        try {
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void destroy() {
        try {
            scheduler.shutdown(true);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}

