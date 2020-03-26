package com.xixi.rocketmq.springboot.starter.example;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

/**
 * @author Chris.Li
 * @desc
 * @date 2019/11/25
 */

public class ScheduledMessageProducer {

    public static void main(String[] args) throws Exception {
        // Instantiate a producer to send scheduled messages
        DefaultMQProducer producer = new DefaultMQProducer("ExampleProducerGroup");
        // Launch producer
        producer.start();
        int totalMessagesToSend = 100;
        for (int i = 0; i < totalMessagesToSend; i++) {
            Message message = new Message("TestTopic", ("Hello scheduled message " + i).getBytes());
            // This message will be delivered to consumer 10 seconds later.
            message.setDelayTimeLevel(3);
            // Send the message
            producer.send(message);
        }

        // Shutdown producer after use.
        producer.shutdown();
    }
}
