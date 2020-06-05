package com.xixi.rocketmq.springboot.starter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Dylan.Lee
 * @since 1.0
 * @date 2019/11/27
 */
@Component
public class RocketMqClientsLoader implements ApplicationContextAware, CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(RocketMqClientsLoader.class);

    private ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    @Override
    public void run(String... args) {
        RocketMqClientsLoader.loading(context);
    }

    private static void loading(ApplicationContext context) {
        for (String className : ClientsRegistry.CONSUMER_CLIENTS) {
            context.getBean(className);
        }
    }

    static class ClientsRegistry {

        private static Set<String> CONSUMER_CLIENTS = new HashSet<>();

        static void registerClient(String type) {
            ClientsRegistry.CONSUMER_CLIENTS.add(type);
        }
    }
}
