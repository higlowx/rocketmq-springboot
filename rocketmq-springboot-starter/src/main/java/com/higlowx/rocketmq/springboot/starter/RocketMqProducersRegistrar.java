package com.higlowx.rocketmq.springboot.starter;

import org.apache.rocketmq.client.AccessChannel;
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
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Set;

/**
 * @author Dylan.Lee
 * @since 1.0
 * @date 2019/11/12
 */

public class RocketMqProducersRegistrar extends RocketMqClientsSupport {


    @Override
    public void registerRocketMqClients(AnnotationMetadata metadata,
                                        BeanDefinitionRegistry registry) {
        ClassPathScanningCandidateComponentProvider scanner = getScanner();
        scanner.setResourceLoader(this.resourceLoader);

        Set<String> basePackages;

        AnnotationTypeFilter annotationTypeFilter = new AnnotationTypeFilter(
                RocketMqProducer.class);

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
                            "@RocketMqProducer can only be specified on an interface");
                    String[] superInterfaces = annotationMetadata.getInterfaceNames();
                    Assert.isTrue(superInterfaces.length == 1,
                            "Decorative class must only extends one interface with BaseRocketMqProducer.class");
                    Assert.isTrue(superInterfaces[0].equals(RocketMqConst.BASE_PRODUCER_CLASSNAME),
                            "Decorative class must extends BaseRocketMqProducer.class");
                    Map<String, Object> attributes = annotationMetadata
                            .getAnnotationAttributes(
                                    RocketMqProducer.class.getCanonicalName());

                    registerRocketMqClients(registry, annotationMetadata, attributes);
                }
            }
        }
    }

    private void registerRocketMqClients(BeanDefinitionRegistry registry,
                                         AnnotationMetadata annotationMetadata, Map<String, Object> attributes) {
        String className = annotationMetadata.getClassName();
        BeanDefinitionBuilder definition = BeanDefinitionBuilder
                .genericBeanDefinition(ProducerFactoryBean.class);
        validate(annotationMetadata, attributes);
        Object group = attributes.get(RocketMqConst.ANNOTATION_PROPERTY_GROUP);
        Object sendMsgTimeout = attributes.get(RocketMqConst.ANNOTATION_PROPERTY_SEND_MSG_TIMEOUT);
        Object vipChannelEnabled = attributes.get(RocketMqConst.ANNOTATION_PROPERTY_VIP_VHANNEL_ENABLED);
        Object isTxMessage = attributes.get(RocketMqConst.ANNOTATION_PROPERTY_IS_TX_MESSAGE);
        Class<?> executor = (Class<?>) attributes.get(RocketMqConst.ANNOTATION_PROPERTY_EXECUTOR);
        Class<?> listener = (Class<?>) attributes.get(RocketMqConst.ANNOTATION_PROPERTY_LISTENER);
        AccessChannel accessChannel = (AccessChannel) attributes.get(RocketMqConst.ANNOTATION_PROPERTY_ACCESS_CHANNEL);

        definition.addPropertyValue(RocketMqConst.PROPERTY_GROUP, group);
        definition.addPropertyValue(RocketMqConst.PROPERTY_SEND_MSG_TIMEOUT, sendMsgTimeout);
        definition.addPropertyValue(RocketMqConst.PROPERTY_VIP_VHANNEL_ENABLED, vipChannelEnabled);
        definition.addPropertyValue(RocketMqConst.PROPERTY_TYPE, className);
        definition.addPropertyValue(RocketMqConst.PROPERTY_IS_TX_MESSAGE, isTxMessage);
        definition.addPropertyValue(RocketMqConst.PROPERTY_EXECUTOR, executor);
        definition.addPropertyValue(RocketMqConst.PROPERTY_LISTENER, listener);
        definition.addPropertyValue(RocketMqConst.PROPERTY_ACCESS_CHANNEL, accessChannel);
        definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);

        registerBeanDefinition(registry, definition, className, attributes);
    }

    private void validate(AnnotationMetadata annotationMetadata, Map<String, Object> attributes) {
        AnnotationAttributes annotation = AnnotationAttributes.fromMap(attributes);
        Assert.hasText(annotation.getString(RocketMqConst.ANNOTATION_PROPERTY_GROUP), "group must be set");
        Assert.isTrue(!annotation.getBoolean(RocketMqConst.ANNOTATION_PROPERTY_IS_TX_MESSAGE)
                        || StringUtils.hasText(annotation.getString(RocketMqConst.ANNOTATION_PROPERTY_LISTENER)),
                "transaction listener must be set when txMessage set");
    }

    @Override
    protected Class getAnnotation() {
        return EnableRocketMqProducers.class;
    }
}
