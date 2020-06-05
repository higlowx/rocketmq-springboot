package com.dylanlee.rocketmq.springboot.starter;

import org.apache.rocketmq.client.AccessChannel;

import java.lang.annotation.*;

/**
 * @author Dylan.Lee
 * @since 1.0
 * @date 2019/11/7
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

    String executor() default "";

    String listener() default "";

    boolean primary() default true;

    AccessChannel accessChannel() default AccessChannel.LOCAL;

}
