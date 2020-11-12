package com.higlowx.rocketmq.springboot.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.higlowx")
@EnableRocketMqProducers(basePackages = "com.higlowx")
@EnableRocketMqConsumers(basePackages = "com.higlowx")
public class Start {

    public static void main(String[] args) {
        SpringApplication.run(Start.class, args);
    }


}
