package top.sharex.oes.core.session;


import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.sharex.oes.core.DestroyListener;
import top.sharex.oes.core.Destroyable;
import top.sharex.oes.core.jobtrigger.RemoveFromSessionJob;
import top.sharex.oes.core.jobtrigger.TriggerFactory;
import top.sharex.oes.core.schedule.OESScheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author danielyang
 * @Date 2017/11/20 17:32
 * 自定义session实现类
 */
public class OESSession {
    private OESScheduler scheduler;
    private Logger logger = LoggerFactory.getLogger(OESSession.class);
    private Map<String, SessionComponent> container = new ConcurrentHashMap<>();
    private String name;

    private Lock lock;

    public static OESSession newSession(String name, OESScheduler scheduler) {
        OESSession session = new OESSession();
        session.scheduler = scheduler;
        session.name = name;
        session.lock = new ReentrantLock();
        return session;
    }

    /**
     * 插入对象，默认有效时间为永久（不会过期）
     *
     * @param id
     * @param object
     */
    public void put(String id, Object object) {
        put(id, object, -1L);
    }

    /**
     * 插入session对象
     *
     * @param id
     * @param object
     * @param timeExpire
     */
    public void put(String id, Object object, long timeExpire) {
        if (object == null)
            return;
        SessionComponent sessionComponent = container.get(id);
        boolean newAdded = false;
        if (sessionComponent == null) {
            lock.lock();
            if ((sessionComponent = container.get(id)) == null) {
                newAdded = true;
                sessionComponent = new SessionComponent();
                container.put(id, sessionComponent);
            }
            lock.unlock();
        }
        lock.lock();
        sessionComponent.lastModifyTime = System.currentTimeMillis();
        sessionComponent.timeExpire = timeExpire;
        if (sessionComponent.component == null || !sessionComponent.component.equals(object)) {
            sessionComponent.component = object;
        }
        lock.unlock();
        //过期时间大于0，需要设定过期时的任务
        if (sessionComponent.timeExpire > 0) {
            if (newAdded) {
                JobDetail jobDetail = RemoveFromSessionJob.generateJobDetail(this, id);
                Trigger trigger = TriggerFactory.getOnceRunTrigger(id, RemoveFromSessionJob
                        .TRIGGER_GROUP, timeExpire);
                scheduler.scheduleJob(jobDetail, trigger);
            } else {
                updateExpiredJob(id, timeExpire);
            }
        }
    }

    /**
     * 更新对应id的上次修改时间（及重置了过期时间）
     *
     * @param id
     */
    public void touch(String id) {
        SessionComponent sessionComponent = container.get(id);
        if (sessionComponent == null)
            return;
        if (sessionComponent.timeExpire > 0)
            updateExpiredJob(id, sessionComponent.timeExpire);
        sessionComponent.lastModifyTime = System.currentTimeMillis();
    }

    /**
     * 获取session中指定类型的对象。由于可能返回Null值，在使用基本类型的时候，建议使用对象类型
     * 即使用Long Integer等而不是long int
     * 对返回值需要进行非空判断
     *
     * @param id
     * @param tClass
     * @param <T>
     * @return
     */
    public <T> T get(String id, Class<T> tClass) {
        Object obj = get(id);
        if (obj == null)
            return null;
        try {
            return tClass.cast(obj);
        } catch (ClassCastException e) {
            logger.error("cast error,class {} can't cast to class {}", obj.getClass().getName(),
                    tClass.getName());
            return null;
        }
    }

    /**
     * 获得session中的对象
     *
     * @param id
     * @return
     */
    public Object get(String id) {
        SessionComponent sessionComponent = container.get(id);
        if (sessionComponent == null) {
            logger.info("component {} is not existed", id);
            return null;
        }
        if (sessionComponent.timeExpire > 0 &&
                (sessionComponent.lastModifyTime <
                        System.currentTimeMillis() - sessionComponent.timeExpire)) {
            logger.info("component {} is invalid(time expired)", id);
            return null;
        }
        return sessionComponent.component;
    }

    /**
     * 删除对应对象
     *
     * @param id
     */
    public void remove(String id) {
        SessionComponent sessionComponent = container.get(id);
        if (sessionComponent == null)
            return;
        lock.lock();
        if ((sessionComponent = container.get(id)) == null)
            return;
        sessionComponent.destroy();
        container.remove(id);
        lock.unlock();
    }


    private void updateExpiredJob(String id, long expireTime) {
        scheduler.rescheduleJob(TriggerKey.triggerKey(id, RemoveFromSessionJob.TRIGGER_GROUP),
                TriggerFactory.getOnceRunTrigger(id, RemoveFromSessionJob.TRIGGER_GROUP, expireTime));
    }

}

/**
 * session内保存的对象，维护了保存在session中需要添加的额外参数
 */
class SessionComponent implements Destroyable {
    /**
     * 对象添加时间
     */
    long lastModifyTime;
    /**
     * 对象的有效时间，小于0的值表示永久有效
     */
    long timeExpire;
    /**
     * 实际保存的对象
     */
    Object component;

    private List<DestroyListener> destroyListeners;

    public SessionComponent() {
        destroyListeners = new ArrayList<>();
    }

    @Override
    public void destroy() {
        if (component instanceof Destroyable) {
            ((Destroyable) component).destroy();
        }
        component = null;
        destroyListeners.forEach(DestroyListener::onDestroy);
        destroyListeners.clear();
    }

    public void addDestroyListener(DestroyListener listener) {
        destroyListeners.add(listener);
    }
}
