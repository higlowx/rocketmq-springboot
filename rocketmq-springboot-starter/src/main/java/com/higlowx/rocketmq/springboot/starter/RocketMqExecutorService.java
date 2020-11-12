package com.higlowx.rocketmq.springboot.starter;

import java.util.concurrent.*;

/**
 * @author Dylan.Lee
 * @date 2019/11/26
 * @since 1.0
 */

public enum RocketMqExecutorService {

    /**
     * singleton enum
     */
    INSTANCE;

    private final ExecutorService instance;

    RocketMqExecutorService() {
        int processorsNum = Runtime.getRuntime().availableProcessors();
        this.instance = new ThreadPoolExecutor(processorsNum / 2, processorsNum, 100, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(2000), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("RocketMqClientTxMsgCheckThread");
                return thread;
            }
        });
    }

    public ExecutorService getInstance() {
        return this.instance;
    }
}
