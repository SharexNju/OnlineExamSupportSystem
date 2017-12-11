package top.sharex.oes.core.jobtrigger;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author danielyang
 * @Date 2017/11/21 16:41
 */
public class JobDemo implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("hello");
    }
}
