package com.higlowx.rocketmq.springboot.starter;

import org.apache.rocketmq.client.consumer.MessageSelector;
import org.apache.rocketmq.client.consumer.PullCallback;
import org.apache.rocketmq.client.consumer.PullResult;
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

public interface PullConsumerClient {

    PullResult pull(final MessageQueue mq, final String subExpression, final long offset,
                    final int maxNums) throws MQClientException, RemotingException, MQBrokerException,
            InterruptedException;

    PullResult pull(final MessageQueue mq, final String subExpression, final long offset,
                    final int maxNums, final long timeout) throws MQClientException, RemotingException,
            MQBrokerException, InterruptedException;

    PullResult pull(final MessageQueue mq, final MessageSelector selector, final long offset,
                    final int maxNums) throws MQClientException, RemotingException, MQBrokerException,
            InterruptedException;

    PullResult pull(final MessageQueue mq, final MessageSelector selector, final long offset,
                    final int maxNums, final long timeout) throws MQClientException, RemotingException, MQBrokerException,
            InterruptedException;

    void pull(final MessageQueue mq, final String subExpression, final long offset, final int maxNums,
              final PullCallback pullCallback) throws MQClientException, RemotingException,
            InterruptedException;

    void pull(final MessageQueue mq, final String subExpression, final long offset, final int maxNums,
              final PullCallback pullCallback, long timeout) throws MQClientException, RemotingException,
            InterruptedException;

    void pull(final MessageQueue mq, final MessageSelector selector, final long offset, final int maxNums,
              final PullCallback pullCallback) throws MQClientException, RemotingException,
            InterruptedException;

    void pull(final MessageQueue mq, final MessageSelector selector, final long offset, final int maxNums,
              final PullCallback pullCallback, long timeout) throws MQClientException, RemotingException,
            InterruptedException;

    PullResult pullBlockIfNotFound(final MessageQueue mq, final String subExpression,
                                   final long offset, final int maxNums) throws MQClientException, RemotingException,
            MQBrokerException, InterruptedException;

    void pullBlockIfNotFound(final MessageQueue mq, final String subExpression, final long offset,
                             final int maxNums, final PullCallback pullCallback) throws MQClientException, RemotingException,
            InterruptedException;

    void updateConsumeOffset(final MessageQueue mq, final long offset) throws MQClientException;

    long fetchConsumeOffset(final MessageQueue mq, final boolean fromStore) throws MQClientException;

    Set<MessageQueue> fetchMessageQueuesInBalance(final String topic) throws MQClientException;

    void sendMessageBack(MessageExt msg, int delayLevel, String brokerName, String consumerGroup)
            throws RemotingException, MQBrokerException, InterruptedException, MQClientException;
}
