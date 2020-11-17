package com.higlowx.rocketmq.springboot.starter;

import org.apache.rocketmq.client.AccessChannel;
import org.apache.rocketmq.client.consumer.listener.MessageListener;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.protocol.heartbeat.ConsumeType;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Proxy;

/**
 * @author Dylan.Li
 * @since 1.0
 * @date 2019/11/25
 */

public class ConsumerFactoryBean implements FactoryBean<Object>, InitializingBean, ApplicationContextAware {

    private ApplicationContext context;
    private Class<?> type;
    private String namesrvAddr;
    private String group;
    private String topic;
    private String tag;
    private boolean vipChannelEnabled;
    private String accessKey;
    private String accessSecret;
    private String listener;
    private ConsumeType consumeType;
    private MessageModel msgModel;
    private ConsumeFromWhere consumeFrom;
    private MessageListener messageListener;
    private AccessChannel accessChannel;


    @Override
    public Object getObject() throws Exception {

        loadingUnifiedConfig();

        loadingMessageListener(listener);

        Consumer consumer = Consumer.Builder.builder()
                .basic(this).msgListener(messageListener)
                .build();

        return Proxy.newProxyInstance(type.getClassLoader(), new Class[]{type}, consumer);
    }

    @Override
    public Class<?> getObjectType() {
        return type;
    }

    @Override
    public void afterPropertiesSet() {
        Assert.notNull(type, "type must be set");
        Assert.hasText(group, "group must be set");
        Assert.hasText(topic, "topic must be set");
        Assert.hasText(listener, "listener must be set");
        Assert.notNull(accessChannel, "accessChannel must be set");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    /**
     * loading unified config
     *
     * @throws NoSuchBeanDefinitionException
     */
    private void loadingUnifiedConfig() throws NoSuchBeanDefinitionException {
        RocketMqUnifiedProperties properties = (RocketMqUnifiedProperties) getBean(RocketMqConst.UNIFIED_CONFIG_BEANNAME);
        Assert.hasText(properties.getNamesrvAddr(), "namesrvAddr must be set in config files for this consumer");
        namesrvAddr = properties.getNamesrvAddr();
        accessKey = properties.getAccessKey();
        accessSecret = properties.getAccessSecret();
    }

    /**
     * loading message listener
     *
     * @param listenerName bean name of defined listener
     */
    private void loadingMessageListener(String listenerName) throws NoSuchBeanDefinitionException {
        messageListener = (MessageListener) getBean(listenerName);
    }

    /**
     * checkout the registered bean by beanName
     *
     * @param beanName bean name
     * @return specified bean
     * @throws NoSuchBeanDefinitionException
     */
    private Object getBean(String beanName) throws NoSuchBeanDefinitionException {
        Object bean = context.getBean(beanName);
        if (ObjectUtils.isEmpty(bean)) {
            throw new NoSuchBeanDefinitionException("class instance not found by bean named " + beanName);
        }
        return bean;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    String getNamesrvAddr() {
        return namesrvAddr;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    boolean isVipChannelEnabled() {
        return vipChannelEnabled;
    }

    public void setVipChannelEnabled(boolean vipChannelEnabled) {
        this.vipChannelEnabled = vipChannelEnabled;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public String getAccessSecret() {
        return accessSecret;
    }

    public String getListener() {
        return listener;
    }

    public void setListener(String listener) {
        this.listener = listener;
    }

    ConsumeType getConsumeType() {
        return consumeType;
    }

    public void setConsumeType(ConsumeType consumeType) {
        this.consumeType = consumeType;
    }

    MessageModel getMsgModel() {
        return msgModel;
    }

    public void setMsgModel(MessageModel msgModel) {
        this.msgModel = msgModel;
    }

    ConsumeFromWhere getConsumeFrom() {
        return consumeFrom;
    }

    public void setConsumeFrom(ConsumeFromWhere consumeFrom) {
        this.consumeFrom = consumeFrom;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public AccessChannel getAccessChannel() {
        return accessChannel;
    }

    public void setAccessChannel(AccessChannel accessChannel) {
        this.accessChannel = accessChannel;
    }
}
