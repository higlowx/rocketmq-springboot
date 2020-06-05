package com.dylanlee.rocketmq.springboot.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.dylanlee")
@EnableRocketMqProducers(basePackages = "com.dylanlee")
@EnableRocketMqConsumers(basePackages = "com.dylanlee")
public class Start {

    public static void main(String[] args) {
        SpringApplication.run(Start.class, args);
    }


}
