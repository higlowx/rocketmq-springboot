package com.xixi.rocketmq.springboot.starter.test;

import com.xixi.rocketmq.springboot.starter.BaseRocketMqConsumer;
import com.xixi.rocketmq.springboot.starter.RocketMqConsumer;
import org.apache.rocketmq.client.AccessChannel;

/**
 * @author Chris.Li
 * @desc
 * @date 2019/11/27
 */
@RocketMqConsumer(group = "GID-msgcenter", tag = "sms"
        , topic = "msgcenter-timing", listener = "msgListener1", accessChannel = AccessChannel.CLOUD)
public interface Consumer1 extends BaseRocketMqConsumer {
}
