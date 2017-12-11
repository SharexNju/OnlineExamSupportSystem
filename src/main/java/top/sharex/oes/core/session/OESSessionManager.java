package top.sharex.oes.core.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.sharex.oes.core.schedule.OESScheduler;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author danielyang
 * @Date 2017/11/21 9:35
 * session的存储池，使用sid获取一个session，sid通常是一个用户的唯一标致名
 */
@Component
public class OESSessionManager {
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * 默认不可用时间：半小时
     */
    private static final int DEFAULT_UNVALID_TIME = 1800 * 1000;

    private Map<String, OESSession> sessionPool;

    private Lock lock;

    @Autowired
    private OESScheduler scheduler;

    @PostConstruct
    public void init() {
        sessionPool = new HashMap<>(DEFAULT_CAPACITY);

        lock = new ReentrantLock();
    }

    public OESSession getSession(String sid) {
        OESSession session = sessionPool.get(sid);
        if (session == null) {
            lock.lock();
            if (sessionPool.get(sid) == null) {
                session = OESSession.newSession(sid, scheduler);
                sessionPool.put(sid, session);
            }
            lock.unlock();
        }
        return session;
    }

    public void deleteSession(String sid) {
        lock.lock();
        sessionPool.remove(sid);
        lock.unlock();
    }
}
