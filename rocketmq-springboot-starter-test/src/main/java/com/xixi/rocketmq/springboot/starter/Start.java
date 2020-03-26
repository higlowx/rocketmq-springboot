package com.xixi.rocketmq.springboot.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.xixi")
@EnableRocketMqProducers(basePackages = "com.xixi")
@EnableRocketMqConsumers(basePackages = "com.xixi")
public class Start {

    public static void main(String[] args) {
        SpringApplication.run(Start.class, args);
    }


}
