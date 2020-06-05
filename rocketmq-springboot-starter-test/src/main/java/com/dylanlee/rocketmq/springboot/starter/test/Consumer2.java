package com.dylanlee.rocketmq.springboot.starter.test;

import com.dylanlee.rocketmq.springboot.starter.BaseRocketMqConsumer;

/**
 * @author Chris.Li
 * @desc
 * @date 2020/1/10
 */
//@RocketMqConsumer(group = "GID-msgcenter", topic = "msgcenter-normal", listener = "msgListener1", accessChannel = AccessChannel.CLOUD)
public interface Consumer2 extends BaseRocketMqConsumer {
}
