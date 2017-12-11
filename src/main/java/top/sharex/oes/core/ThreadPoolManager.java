package top.sharex.oes.core;

import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author danielyang
 * @Date 2017/11/21 10:31
 * 定义各类线程池
 */
@Component
public class ThreadPoolManager {
    private static final int TASK_SERVICE_POOL_SIZE = 10;
    private ExecutorService taskService = Executors.newFixedThreadPool(TASK_SERVICE_POOL_SIZE);

    public ExecutorService getTaskService() {
        return taskService;
    }
}
