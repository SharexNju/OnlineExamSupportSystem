package top.sharex.oes.core;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.sharex.oes.core.jobtrigger.JobDemo;
import top.sharex.oes.core.jobtrigger.TriggerFactory;
import top.sharex.oes.core.schedule.OESScheduler;

/**
 * @author danielyang
 * @Date 2017/11/21 16:40
 */
@Component
public class ScheduleDemo {
    @Autowired
    OESScheduler scheduler;

    //示例代码
    public void test() {
        JobDetail jobDetail = JobBuilder.newJob(JobDemo.class)//添加任务实现类
                .withIdentity("name", "group")//设置名字和组
                .build();


        Trigger trigger = TriggerFactory.getOnceRunTrigger("name", "group", 1000);

        scheduler.scheduleJob(jobDetail, trigger);
    }
}
