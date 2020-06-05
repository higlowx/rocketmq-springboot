package com.dylanlee.rocketmq.springboot.starter.test;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Chris.Li
 * @desc
 * @date 2019/11/27
 */
@Component
public class MsgListener1 implements MessageListenerConcurrently {


    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        System.out.println("******************消费***********************");
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
