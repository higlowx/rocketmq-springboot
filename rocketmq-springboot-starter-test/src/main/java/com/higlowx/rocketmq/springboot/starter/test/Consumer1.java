package com.higlowx.rocketmq.springboot.starter.test;

import com.higlowx.rocketmq.springboot.starter.BaseRocketMqConsumer;
import com.higlowx.rocketmq.springboot.starter.RocketMqConsumer;
import org.apache.rocketmq.client.AccessChannel;

/**
 * @author Dylan.Li
 * @desc
 * @date 2019/11/27
 */
@RocketMqConsumer(group = "", tag = ""
        , topic = "", listener = Object.class, accessChannel = AccessChannel.CLOUD)
public interface Consumer1 extends BaseRocketMqConsumer {
}
