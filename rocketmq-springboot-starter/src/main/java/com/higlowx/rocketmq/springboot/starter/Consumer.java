package com.higlowx.rocketmq.springboot.starter;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.acl.common.AclClientRPCHook;
import org.apache.rocketmq.acl.common.SessionCredentials;
import org.apache.rocketmq.client.AccessChannel;
import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.MessageListener;
import org.apache.rocketmq.client.consumer.rebalance.AllocateMessageQueueAveragely;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.protocol.heartbeat.ConsumeType;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.apache.rocketmq.remoting.RPCHook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author Dylan.Li
 * @since 1.0
 * @date 2019/11/25
 */

public class Consumer implements InvocationHandler {

    private static final Logger LOG = LoggerFactory.getLogger(Consumer.class);

    private Class<?> type;
    private DefaultMQPushConsumer pushConsumer;
    private DefaultMQPullConsumer pullConsumer;


    private Consumer(Class<?> type, DefaultMQPushConsumer pushConsumer) {
        this.type = type;
        this.pushConsumer = pushConsumer;
    }

    private Consumer(Class<?> type, DefaultMQPullConsumer pullConsumer) {
        this.type = type;
        this.pullConsumer = pullConsumer;
    }

    static class Builder {

        private Class<?> type;
        private String namesrvAddr;
        private String group;
        private String topic;
        private String tag;
        private boolean vipChannelEnabled;
        private String accessKey;
        private String accessSecret;
        private MessageListener msgListener;
        private ConsumeType consumeType;
        private MessageModel msgModel;
        private ConsumeFromWhere consumeFrom;
        private AccessChannel accessChannel;
        private DefaultMQPushConsumer pushConsumer;
        private DefaultMQPullConsumer pullConsumer;

        private Builder() {

        }

        static Builder builder() {
            return new Builder();
        }

        Builder basic(ConsumerFactoryBean factoryBean) {
            type = factoryBean.getType();
            namesrvAddr = factoryBean.getNamesrvAddr();
            group = factoryBean.getGroup();
            topic = factoryBean.getTopic();
            tag = factoryBean.getTag();
            consumeFrom = factoryBean.getConsumeFrom();
            consumeType = factoryBean.getConsumeType();
            msgModel = factoryBean.getMsgModel();
            vipChannelEnabled = factoryBean.isVipChannelEnabled();
            accessKey = factoryBean.getAccessKey();
            accessSecret = factoryBean.getAccessSecret();
            accessChannel = factoryBean.getAccessChannel();
            return this;
        }

        Builder msgListener(MessageListener msgListener) {
            this.msgListener = msgListener;
            return this;
        }

        Consumer build() throws MQClientException {
            if (ConsumeType.CONSUME_ACTIVELY.equals(consumeType)) {
                initPullConsumer();
                return new Consumer(type, pullConsumer);
            }
            initPushConsumer();
            return new Consumer(type, pushConsumer);
        }

        private void initPushConsumer() throws MQClientException {

            RPCHook rpcHook = StringUtils.isAnyBlank(accessKey, accessSecret) ?
                    null : new AclClientRPCHook(new SessionCredentials(accessKey, accessSecret));

            DefaultMQPushConsumer pushConsumer = new DefaultMQPushConsumer(group, rpcHook, new AllocateMessageQueueAveragely());

            pushConsumer.setNamesrvAddr(namesrvAddr);
            pushConsumer.setVipChannelEnabled(vipChannelEnabled);
            pushConsumer.setConsumeFromWhere(consumeFrom);
            pushConsumer.setMessageModel(msgModel);
            pushConsumer.subscribe(topic, tag);
            pushConsumer.setMessageListener(msgListener);
            pushConsumer.setAccessChannel(accessChannel);

            this.pushConsumer = pushConsumer;
            this.pushConsumer.start();
            LOG.info("Start RocketMQ push consumer: name is {}, topic is {}, tag is {}, groupId is {}", type.getSimpleName(), topic, tag, group);
        }

        private void initPullConsumer() throws MQClientException {

        }

    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        if ("toString".equals(methodName)) {
            return toString();
        } else if ("equals".equals(methodName)) {
            return equals(args[0]);
        } else if ("hashCode".equals(methodName)) {
            return hashCode();
        }
        Class[] parameterTypes = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            parameterTypes[i] = null == args[i] ? Object.class : args[i].getClass();
        }
        proxy = ObjectUtils.isEmpty(pullConsumer) ? pushConsumer : pullConsumer;
        method = proxy.getClass().getMethod(methodName, parameterTypes);
        return method.invoke(proxy, args);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Consumer that = (Consumer) o;
        return Objects.equals(this.type, that.type) &&
                Objects.equals(this.pushConsumer, that.pushConsumer) &&
                Objects.equals(this.pullConsumer, that.pullConsumer);
    }

    @Override
    public String toString() {
        return type.getSimpleName() + "{classType=" + type + ", pushConsumer='" + pushConsumer
                + ", pullConsumer='" + pushConsumer + "}";
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.type, this.pushConsumer, this.pullConsumer);
    }

}
