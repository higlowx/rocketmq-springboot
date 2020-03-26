package com.xixi.rocketmq.springboot.starter;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author Chris.Li
 * @desc
 * @date 2019/11/12
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(RocketMqProducersRegistrar.class)
public @interface EnableRocketMqProducers {

    String[] value() default {};

    String[] basePackages() default {};

    Class<?>[] basePackageClasses() default {};

    ComponentScan.Filter[] includeFilters() default {};

    ComponentScan.Filter[] excludeFilters() default {};

}
