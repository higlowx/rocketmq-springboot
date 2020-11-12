package com.higlowx.rocketmq.springboot.starter;

import org.apache.rocketmq.client.AccessChannel;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.protocol.heartbeat.ConsumeType;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.lang.annotation.*;

/**
 * @author Dylan.Lee
 * @date 2019/11/21
 * @since 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RocketMqConsumer {

    String qualifier() default "";

    String group() default "";

    String topic() default "";

    String[] tag() default {"*"};

    Class<?> listener();

    boolean vipChannelEnabled() default false;

    ConsumeType consumeType() default ConsumeType.CONSUME_PASSIVELY;

    MessageModel msgModel() default MessageModel.CLUSTERING;

    ConsumeFromWhere consumeFrom() default ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET;

    boolean primary() default true;

    AccessChannel accessChannel() default AccessChannel.LOCAL;


}
