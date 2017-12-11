package top.sharex.oes.core.jobtrigger;

import org.quartz.Trigger;
import top.sharex.oes.util.DateUtil;

import java.util.Date;

import static org.quartz.SimpleScheduleBuilder.*;
import static org.quartz.TriggerBuilder.*;

/**
 * @author danielyang
 * @Date 2017/11/21 13:33
 */
public class TriggerFactory {
    /**
     * 获得只执行一次的Trigger
     *
     * @param name             trigger名字
     * @param group            trigger组
     * @param startTimeFromNow 距离现在的开始时间
     * @return
     */
    public static Trigger getOnceRunTrigger(String name, String group, long startTimeFromNow) {
        return getOnceRunTrigger(name, group, DateUtil.fromNow(startTimeFromNow));
    }

    /**
     * @param name    trigger 名字
     * @param group   trigger组
     * @param startAt 开始的时间
     * @return
     */
    public static Trigger getOnceRunTrigger(String name, String group, Date startAt) {
        return newTrigger().startAt(startAt).withIdentity(name, group)
                .withSchedule(simpleSchedule()).build();
    }

}
