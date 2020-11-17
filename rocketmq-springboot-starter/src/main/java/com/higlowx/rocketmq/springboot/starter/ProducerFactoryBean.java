package com.higlowx.rocketmq.springboot.starter;

import org.apache.rocketmq.client.AccessChannel;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;

/**
 * @author Dylan.Li
 * @date 2019/11/11
 * @since 1.0
 */
public class ProducerFactoryBean<T> implements FactoryBean<T>, InitializingBean, ApplicationContextAware {

    private ApplicationContext context;
    private Class<?> type;
    private String group;
    private Integer sendMsgTimeout;
    private String namesrvAddr;
    private boolean vipChannelEnabled;
    private boolean txMessage;
    private String accessKey;
    private String accessSecret;
    private Class<?> executor = void.class;
    private Class<?> listener = void.class;
    private ExecutorService executorService;
    private TransactionListener transactionListener;
    private AccessChannel accessChannel;


    @Override
    @SuppressWarnings("unchecked")
    public T getObject() throws Exception {

        loadingUnifiedConfig();

        Producer.Builder producer = Producer.Builder.builder()
                .basic(this);

        if (!txMessage) {
            return (T) Proxy.newProxyInstance(type.getClassLoader(),
                    new Class[]{type}, producer.build());
        }

        loadingExecutorService(executor);
        loadingTransactionListener(listener);

        return (T) Proxy.newProxyInstance(type.getClassLoader(), new Class[]{type},
                producer.executor(executorService).transactionListener(transactionListener).build());
    }


    @Override
    public Class<?> getObjectType() {
        return type;
    }

    @Override
    public void afterPropertiesSet() {
        Assert.hasText(group, "group must be set");
        Assert.notNull(type, "type must be set");
        Assert.notNull(accessChannel, "accessChannel must be set");
        Assert.isTrue(!txMessage || void.class != listener,
                "transaction listener must be set when txMessage set");
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
        RocketMqUnifiedProperties properties = getBean(RocketMqUnifiedProperties.class);
        Assert.hasText(properties.getNamesrvAddr(), "namesrvAddr must be set in config files  for this producer");
        namesrvAddr = properties.getNamesrvAddr();
        accessKey = properties.getAccessKey();
        accessSecret = properties.getAccessSecret();
    }

    /**
     * loading executor for local transaction executing and checking
     *
     * @throws NoSuchBeanDefinitionException
     */
    private void loadingExecutorService(Class<?> executorType) throws NoSuchBeanDefinitionException {

        //use default executor
        if (void.class == executorType) {
            executorService = RocketMqExecutorService.INSTANCE.getInstance();
            return;
        }
        //use defined executor
        executorService = (ExecutorService) getBean(executorType);
    }

    /**
     * loading local transaction listener
     *
     * @throws NoSuchBeanDefinitionException
     */
    private void loadingTransactionListener(Class<?> listenerType) throws NoSuchBeanDefinitionException {
        transactionListener = (TransactionListener) getBean(listenerType);
    }

    /**
     * checkout the registered bean by beanName
     *
     * @param beanName bean name
     * @return specified bean
     * @throws NoSuchBeanDefinitionException
     */
    @Deprecated
    private Object getBean(String beanName) throws NoSuchBeanDefinitionException {
        Object bean = context.getBean(beanName);
        if (ObjectUtils.isEmpty(bean)) {
            throw new NoSuchBeanDefinitionException("class instance not found by bean named " + beanName);
        }
        return bean;
    }

    /**
     * checkout the registered bean by beanName
     *
     * @param classType bean type
     * @return specified bean
     * @throws NoSuchBeanDefinitionException
     */
    private <T> T getBean(Class<T> classType) throws NoSuchBeanDefinitionException {
        T bean = context.getBean(classType);
        if (ObjectUtils.isEmpty(bean)) {
            throw new NoSuchBeanDefinitionException("class instance not found by bean type " + classType.getSimpleName());
        }
        return bean;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    Integer getSendMsgTimeout() {
        return sendMsgTimeout;
    }

    public void setSendMsgTimeout(Integer sendMsgTimeout) {
        this.sendMsgTimeout = sendMsgTimeout;
    }

    String getNamesrvAddr() {
        return namesrvAddr;
    }

    boolean isVipChannelEnabled() {
        return vipChannelEnabled;
    }

    public void setVipChannelEnabled(boolean vipChannelEnabled) {
        this.vipChannelEnabled = vipChannelEnabled;
    }

    boolean isTxMessage() {
        return txMessage;
    }

    public void setTxMessage(boolean txMessage) {
        this.txMessage = txMessage;
    }

    public Class<?> getExecutor() {
        return executor;
    }

    public void setExecutor(Class<?> executor) {
        this.executor = executor;
    }

    public Class<?> getListener() {
        return listener;
    }

    public void setListener(Class<?> listener) {
        this.listener = listener;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public String getAccessSecret() {
        return accessSecret;
    }

    public AccessChannel getAccessChannel() {
        return accessChannel;
    }

    public void setAccessChannel(AccessChannel accessChannel) {
        this.accessChannel = accessChannel;
    }
}
