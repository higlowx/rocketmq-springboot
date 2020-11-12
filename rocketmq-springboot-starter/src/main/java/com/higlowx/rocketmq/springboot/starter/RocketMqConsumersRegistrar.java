package com.higlowx.rocketmq.springboot.starter;

import org.apache.rocketmq.client.AccessChannel;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.protocol.heartbeat.ConsumeType;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

/**
 * @author Dylan.Lee
 * @since 1.0
 * @date 2019/11/25
 */

public class RocketMqConsumersRegistrar extends RocketMqClientsSupport {

    @Override
    protected void registerRocketMqClients(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        ClassPathScanningCandidateComponentProvider scanner = getScanner();
        scanner.setResourceLoader(this.resourceLoader);

        Set<String> basePackages;

        AnnotationTypeFilter annotationTypeFilter = new AnnotationTypeFilter(
                RocketMqConsumer.class);

        scanner.addIncludeFilter(annotationTypeFilter);
        basePackages = getBasePackages(metadata);

        for (String basePackage : basePackages) {
            Set<BeanDefinition> candidateComponents = scanner
                    .findCandidateComponents(basePackage);
            for (BeanDefinition candidateComponent : candidateComponents) {
                if (candidateComponent instanceof AnnotatedBeanDefinition) {
                    // verify annotated class is an interface
                    AnnotatedBeanDefinition beanDefinition = (AnnotatedBeanDefinition) candidateComponent;
                    AnnotationMetadata annotationMetadata = beanDefinition.getMetadata();
                    Assert.isTrue(annotationMetadata.isInterface(),
                            "@RocketMqConsumer can only be specified on an interface");
                    String[] superInterfaces = annotationMetadata.getInterfaceNames();
                    Assert.isTrue(superInterfaces.length == 1,
                            "Decorative class must only extends one interface with BaseRocketMqConsumer.class");
                    Assert.isTrue(superInterfaces[0].equals(RocketMqConst.BASE_CONSUMER_CLASSNAME),
                            "Decorative class must extends BaseRocketMqConsumer.class");
                    Map<String, Object> attributes = annotationMetadata
                            .getAnnotationAttributes(
                                    RocketMqConsumer.class.getCanonicalName());

                    registerRocketMqClients(registry, annotationMetadata, attributes);
                }
            }
        }
    }

    private void registerRocketMqClients(BeanDefinitionRegistry registry,
                                         AnnotationMetadata annotationMetadata, Map<String, Object> attributes) {
        String className = annotationMetadata.getClassName();
        BeanDefinitionBuilder definition = BeanDefinitionBuilder
                .genericBeanDefinition(ConsumerFactoryBean.class);
        validate(annotationMetadata, attributes);
        Object group = attributes.get(RocketMqConst.ANNOTATION_PROPERTY_GROUP);
        Object vipChannelEnabled = attributes.get(RocketMqConst.ANNOTATION_PROPERTY_VIP_VHANNEL_ENABLED);
        Object listener = attributes.get(RocketMqConst.ANNOTATION_PROPERTY_LISTENER);
        Object topic = attributes.get(RocketMqConst.ANNOTATION_PROPERTY_TOPIC);
        String[] tags = (String[]) attributes.get(RocketMqConst.ANNOTATION_PROPERTY_TAG);
        ConsumeType consumeType = (ConsumeType) attributes.get(RocketMqConst.ANNOTATION_PROPERTY_CONSUME_TYPE);
        MessageModel messageModel = (MessageModel) attributes.get(RocketMqConst.ANNOTATION_PROPERTY_MSG_MODEL);
        ConsumeFromWhere consumeFromWhere = (ConsumeFromWhere) attributes.get(RocketMqConst.ANNOTATION_PROPERTY_CONSUME_FROM);
        AccessChannel accessChannel = (AccessChannel) attributes.get(RocketMqConst.ANNOTATION_PROPERTY_ACCESS_CHANNEL);

        definition.addPropertyValue(RocketMqConst.PROPERTY_GROUP, group);
        definition.addPropertyValue(RocketMqConst.PROPERTY_VIP_VHANNEL_ENABLED, vipChannelEnabled);
        definition.addPropertyValue(RocketMqConst.PROPERTY_TYPE, className);
        definition.addPropertyValue(RocketMqConst.PROPERTY_LISTENER, listener);
        definition.addPropertyValue(RocketMqConst.PROPERTY_TOPIC, topic);
        StringBuffer tag = new StringBuffer();
        Arrays.asList(tags).forEach(t -> tag.append("||").append(t));
        definition.addPropertyValue(RocketMqConst.PROPERTY_TAG, tag.toString().substring(2));
        definition.addPropertyValue(RocketMqConst.PROPERTY_CONSUME_TYPE, consumeType);
        definition.addPropertyValue(RocketMqConst.PROPERTY_MSG_MODEL, messageModel);
        definition.addPropertyValue(RocketMqConst.PROPERTY_CONSUME_FROM, consumeFromWhere);
        definition.addPropertyValue(RocketMqConst.PROPERTY_ACCESS_CHANNEL, accessChannel);
        definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);

        registerBeanDefinition(registry, definition, className, attributes);

        RocketMqClientsLoader.ClientsRegistry.registerClient(className);
    }

    private void validate(AnnotationMetadata annotationMetadata, Map<String, Object> attributes) {
        AnnotationAttributes annotation = AnnotationAttributes.fromMap(attributes);
        Assert.hasText(annotation.getString(RocketMqConst.ANNOTATION_PROPERTY_GROUP), "group must be set");
        Assert.hasText(annotation.getString(RocketMqConst.ANNOTATION_PROPERTY_TOPIC), "topic must be set");
        Assert.hasText(annotation.getString(RocketMqConst.ANNOTATION_PROPERTY_LISTENER), "listener must be set");
    }

    @Override
    protected Class getAnnotation() {
        return EnableRocketMqConsumers.class;
    }
}
