package com.dylanlee.rocketmq.springboot.starter;

import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.Collection;
import java.util.List;

/**
 * @author Dylan.Lee
 * @since 1.0
 * @date 2019/11/11
 */
public interface BaseRocketMqProducer {

    List<MessageQueue> fetchPublishMessageQueues(final String topic) throws Exception;

    SendResult send(final Message msg) throws Exception;

    SendResult send(final Message msg, final long timeout) throws Exception;

    void send(final Message msg, final SendCallback sendCallback) throws Exception;

    void sendOneway(final Message msg) throws Exception;

    SendResult send(final Message msg, final MessageQueue mq) throws Exception;

    SendResult send(final Message msg, final MessageQueue mq, final long timeout) throws Exception;

    void send(final Message msg, final MessageQueue mq, final SendCallback sendCallback) throws Exception;

    void sendOneway(final Message msg, final MessageQueue mq) throws Exception;

    SendResult send(final Message msg, final MessageQueueSelector selector, final Object arg) throws Exception;

    SendResult send(final Message msg, final MessageQueueSelector selector, final Object arg, final long timeout) throws Exception;

    void send(final Message msg, final MessageQueueSelector selector, final Object arg, final SendCallback sendCallback) throws Exception;

    void sendOneway(final Message msg, final MessageQueueSelector selector, final Object arg) throws Exception;

    TransactionSendResult sendMessageInTransaction(final Message msg, final Object arg) throws Exception;

    SendResult send(final Collection<Message> msgs) throws Exception;

    SendResult send(final Collection<Message> msgs, final long timeout) throws Exception;

    SendResult send(final Collection<Message> msgs, final MessageQueue mq) throws Exception;

    SendResult send(final Collection<Message> msgs, final MessageQueue mq, final long timeout) throws Exception;
}
