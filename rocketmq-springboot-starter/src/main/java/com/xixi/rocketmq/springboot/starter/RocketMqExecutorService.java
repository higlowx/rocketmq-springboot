package com.xixi.rocketmq.springboot.starter;

import java.util.concurrent.*;

/**
 * @author Dylan.Lee
 * @since 1.0
 * @date 2019/11/26
 */

public enum RocketMqExecutorService {

    /**
     * singleton enum
     */
    INSTANCE;

    private ExecutorService instance;

    RocketMqExecutorService() {
        int processorsNum = Runtime.getRuntime().availableProcessors();
        ExecutorService instance =
                new ThreadPoolExecutor(processorsNum / 2, processorsNum, 100, TimeUnit.SECONDS,
                        new ArrayBlockingQueue<Runnable>(2000), new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread thread = new Thread(r);
                        thread.setName("RocketMqClientTxMsgCheckThread");
                        return thread;
                    }
                });
        this.instance = instance;
    }

    public ExecutorService getInstance() {
        return this.instance;
    }
}
