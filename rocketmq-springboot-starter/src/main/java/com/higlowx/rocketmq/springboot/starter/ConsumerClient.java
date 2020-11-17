package com.higlowx.rocketmq.springboot.starter;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.Set;

/**
 * @author Dylan.Li
 * @since 1.0
 * @date 2019/11/27
 */

public interface ConsumerClient {

    void start() throws MQClientException;

    void shutdown();

    void sendMessageBack(final MessageExt msg, final int delayLevel, final String brokerName)
            throws RemotingException, MQBrokerException, InterruptedException, MQClientException;

    Set<MessageQueue> fetchSubscribeMessageQueues(final String topic) throws MQClientException;
}
