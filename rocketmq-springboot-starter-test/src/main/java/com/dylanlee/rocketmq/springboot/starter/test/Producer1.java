package com.dylanlee.rocketmq.springboot.starter.test;

import com.dylanlee.rocketmq.springboot.starter.BaseRocketMqProducer;
import com.dylanlee.rocketmq.springboot.starter.RocketMqProducer;
import org.apache.rocketmq.client.AccessChannel;

/**
 * @author Chris.Li
 * @desc
 * @date 2019/11/11
 */
@RocketMqProducer(group = "GID-msgcenter", accessChannel = AccessChannel.CLOUD)
public interface Producer1 extends BaseRocketMqProducer {

}
