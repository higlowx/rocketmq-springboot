package com.xixi.rocketmq.springboot.starter;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.acl.common.AclClientRPCHook;
import org.apache.rocketmq.acl.common.SessionCredentials;
import org.apache.rocketmq.client.AccessChannel;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.remoting.RPCHook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.ExecutorService;

/**
 * @author Dylan.Lee
 * @since 1.0
 * @date 2019/11/14
 */
public class Producer implements InvocationHandler {

    private static final Logger LOG = LoggerFactory.getLogger(Producer.class);

    private Class<?> type;
    private DefaultMQProducer producer;

    private Producer(Class classType, DefaultMQProducer producer) {
        this.type = classType;
        this.producer = producer;
    }

    static class Builder {

        private Class<?> type;
        private String group;
        private Integer sendMsgTimeout;
        private String namesrvAddr;
        private boolean vipChannelEnabled;
        private boolean txMessage;
        private String accessKey;
        private String accessSecret;
        private DefaultMQProducer producer;
        private ExecutorService executorService;
        private TransactionListener transactionListener;
        private AccessChannel accessChannel;


        private Builder() {
        }

        static Builder builder() {
            return new Builder();
        }

        Builder basic(ProducerFactoryBean factoryBean) {
            type = factoryBean.getType();
            namesrvAddr = factoryBean.getNamesrvAddr();
            group = factoryBean.getGroup();
            vipChannelEnabled = factoryBean.isVipChannelEnabled();
            sendMsgTimeout = factoryBean.getSendMsgTimeout();
            txMessage = factoryBean.isTxMessage();
            accessKey = factoryBean.getAccessKey();
            accessSecret = factoryBean.getAccessSecret();
            accessChannel = factoryBean.getAccessChannel();
            return this;
        }

        Builder executor(ExecutorService executorService) {
            this.executorService = executorService;
            return this;
        }

        Builder transactionListener(TransactionListener transactionListener) {
            this.transactionListener = transactionListener;
            return this;
        }

        Producer build() throws MQClientException {
            initProducer();
            return new Producer(type, producer);
        }


        private void initProducer() throws MQClientException {

            DefaultMQProducer producer = null;

            RPCHook rpcHook = StringUtils.isAnyBlank(accessKey, accessSecret) ?
                    null : new AclClientRPCHook(new SessionCredentials(accessKey, accessSecret));

            if (txMessage) {
                TransactionMQProducer txProducer = new TransactionMQProducer(group, rpcHook);
                txProducer.setNamesrvAddr(namesrvAddr);
                txProducer.setSendMsgTimeout(sendMsgTimeout);
                txProducer.setSendMessageWithVIPChannel(vipChannelEnabled);
                txProducer.setExecutorService(executorService);
                txProducer.setTransactionListener(transactionListener);
                producer = txProducer;
            } else {
                producer = new DefaultMQProducer(group, rpcHook);
                producer.setNamesrvAddr(namesrvAddr);
                producer.setSendMsgTimeout(sendMsgTimeout);
                producer.setSendMessageWithVIPChannel(vipChannelEnabled);
            }
            producer.setAccessChannel(accessChannel);

            this.producer = producer;
            //start producer
            this.producer.start();
            LOG.info("Start RocketMQ producer: name is {}, groupId is {}", type.getSimpleName(), group);
        }

    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        if ("toString".equals(methodName)) {
            return toString();
        } else if ("hashCode".equals(methodName)) {
            return hashCode();
        } else if ("equals".equals(methodName)) {
            return equals(args[0]);
        }
        Class[] parameterTypes = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            parameterTypes[i] = null == args[i] ? Object.class : args[i].getClass();
        }
        Method method1 = producer.getClass().getMethod(method.getName(), parameterTypes);
        return method1.invoke(producer, args);
    }

    @Override
    public String toString() {
        return type.getSimpleName() + "{classType=" + type + ", producer='" + producer + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Producer that = (Producer) o;
        return Objects.equals(this.type, that.type) &&
                Objects.equals(this.producer, that.producer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.type, this.producer);
    }


}


