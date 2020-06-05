package com.dylanlee.rocketmq.springboot.starter;

import org.apache.rocketmq.client.AccessChannel;

import java.lang.annotation.*;

/**
 * @author Dylan.Lee
 * @date 2019/11/7
 * @since 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RocketMqProducer {

    String qualifier() default "";

    String group() default "";

    int sendMsgTimeout() default 10000;

    boolean vipChannelEnabled() default false;

    boolean txMessage() default false;

    Class<?> executor();

    Class<?> listener();

    boolean primary() default true;

    AccessChannel accessChannel() default AccessChannel.LOCAL;

}
