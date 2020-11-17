package com.higlowx.rocketmq.springboot.starter;

import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageAccessor;

import java.util.Date;

/**
 * @author Dylan.Li
 * @since 1.0
 * @date 2020/1/9
 */

public class MessageWrapper {

    /**
     * 设置定时消息
     *
     * @param message
     * @param date
     * @return
     */
    public static void toTimer(Message message, Date date) {
        MessageAccessor.putProperty(message, RocketMqConst.USER_PROPERTY_STARTDELIVERTIME, String.valueOf(date.getTime()));
    }

    /**
     * 设置分区顺序消息
     *
     * @param message
     * @param shardingKey
     */
    public static void toSharding(Message message, String shardingKey) {
        MessageAccessor.putProperty(message, RocketMqConst.USER_PROPERTY_SHARDINGKEY, shardingKey);
    }
}
