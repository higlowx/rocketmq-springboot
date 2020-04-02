# rocketmq-springboot-starter
一种可以迅速接入RocketMQ能力的spring boot快捷启动依赖

## 简介
这里提供一种基于springboot脚手架、注解以及TCP协议的快速集成方法，兼容原生开源版本rocketmq以及阿里云rocketmq，生产与消费
实现部分在保留原生写法的基础上，依托spring强大的bean托管能力，减少对业务代码的侵入。

## 快速开始

### 配置
引入Maven依赖（现阶段还不支持其他方式的引入）。
```mxml
<dependency>
    <groupId>com.xixi</groupId>
    <artifactId>rocketmq-springboot-starter</artifactId>
    <version>{VERSION}</version>
</dependency>
```
配置文件，以下仅示例springboot原生的properties配置，yaml等其他方式请各位开发者自行类比。
注意：此处使用单个应用仅仅接入单个rocketmq集群的方式。
```properties
rocketmq.namesrvAddr={YOUR NAMESRV ADDR}
rocketmq.accessKey={YOUR ACCESSKEY}
rocketmq.accessSecret={YOUR ACCESS SECRET}
```
按需要在启动类中开启生产实例注册与消费者实例注册，对应的注解分别为<u></u><font color=#FF0000>@EnableRocketMqProducers</font>，<u></u><font color=#FF0000>@EnableRocketMqConsumers</font>
### 