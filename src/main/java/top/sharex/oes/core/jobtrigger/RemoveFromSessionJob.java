package top.sharex.oes.core.jobtrigger;

import org.quartz.*;
import top.sharex.oes.core.session.OESSession;

import java.util.HashMap;
import java.util.Map;

import static org.quartz.JobBuilder.*;

/**
 * @author danielyang
 * @Date 2017/11/21 13:29
 */
public class RemoveFromSessionJob implements Job {
    public static final String SESSION = "session";
    public static final String S_ID = "sid";

    public static final String TRIGGER_GROUP = "REMOVE_TRIGGER";
    public static final String JOB_GROUP = "REMOVE_JOB";


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDetail detail = jobExecutionContext.getJobDetail();
        OESSession session = (OESSession) detail.getJobDataMap().get(SESSION);
        String sid = detail.getJobDataMap().getString(S_ID);
        session.remove(sid);
    }

    public static JobDetail generateJobDetail(OESSession session, String sid) {
        Map<String, Object> data = new HashMap<>();
        data.put(SESSION, session);
        data.put(S_ID, sid);
        return newJob(RemoveFromSessionJob.class).usingJobData(new JobDataMap(data))
                .withIdentity(sid, JOB_GROUP).build();
    }
}
