package com.xixi.rocketmq.springboot.starter;

import org.apache.rocketmq.client.consumer.MessageSelector;
import org.apache.rocketmq.client.exception.MQClientException;

/**
 * @author Chris.Li
 * @desc
 * @date 2019/11/27
 */

public interface PushConsumerClient {

    void subscribe(final String topic, final String subExpression) throws MQClientException;

    void subscribe(final String topic, final MessageSelector selector) throws MQClientException;

    void unsubscribe(final String topic);

    void updateCorePoolSize(int corePoolSize);

    void suspend();

    void resume();
}
