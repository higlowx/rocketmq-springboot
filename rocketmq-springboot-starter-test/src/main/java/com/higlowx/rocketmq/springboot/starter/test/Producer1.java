package com.higlowx.rocketmq.springboot.starter.test;

import com.higlowx.rocketmq.springboot.starter.BaseRocketMqProducer;
import com.higlowx.rocketmq.springboot.starter.RocketMqProducer;
import org.apache.rocketmq.client.AccessChannel;

/**
 * @author Dylan.Li
 * @desc
 * @date 2019/11/11
 */
@RocketMqProducer(group = "", accessChannel = AccessChannel.CLOUD)
public interface Producer1 extends BaseRocketMqProducer {

}
