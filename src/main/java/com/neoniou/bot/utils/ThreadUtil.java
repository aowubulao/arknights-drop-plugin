package com.neoniou.bot.utils;

import java.util.concurrent.*;

/**
 * @author Neo.Zzj
 * @date 2021/1/6
 */
public class ThreadUtil {

    private static ExecutorService threadPool;

    public static void createDefaultThreadPool(int core) {
        createThreadPool(core);
    }

    private static void createThreadPool(int core) {
        threadPool = new ThreadPoolExecutor(core,
                core,
                1L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy());
    }

    public static ExecutorService getThreadPool() {
        return threadPool;
    }

    public static void execute(Runnable command) {
        threadPool.execute(command);
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
