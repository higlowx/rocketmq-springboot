# rocketmq-springboot-starter
一种可以迅速接入RocketMQ能力的spring boot快捷启动依赖

## 简介
这里提供一种基于springboot脚手架、注解以及TCP协议的快速集成方法，兼容原生开源版本rocketmq以及阿里云rocketmq，生产与消费
实现部分在保留原生写法的基础上，依托spring强大的bean托管能力，减少对业务代码的侵入。

## 快速开始

引入Maven依赖（现阶段还不支持其他方式的引入）。
```mxml
<dependency>
    <groupId>com.higlowx</groupId>
    <artifactId>rocketmq-springboot-starter</artifactId>
    <version>{VERSION}</version>
</dependency>
```
编辑配置文件，以下仅示例springboot原生的properties配置，yaml等其他方式请各位开发者自行类比。
注意：此处使用单个应用仅仅接入单个rocketmq集群的方式。
```properties
rocketmq.namesrvAddr={YOUR NAMESRV ADDR}
rocketmq.accessKey={YOUR ACCESSKEY}
rocketmq.accessSecret={YOUR ACCESS SECRET}
```
按需要在启动类中开启生产实例注册与消费者实例注册，对应的注解分别为：

生产者
```java
@EnableRocketMqProducers
public class Start {}
```
消费者
```java
@EnableRocketMqConsumers
public class Start {}
```

在开启客户端之后，按需创建生产者或者消费者的接口类（必须依赖顶级接口`BaseRocketMqProducer.class`或者`BaseRocketMqConsumer.class`），以下已生产者的例子

```java
@RocketMqProducer(group = "GID", accessChannel = AccessChannel.CLOUD)
public interface Producer1 extends BaseRocketMqProducer {

}
```
简单介绍一下`@RocketMqProducer`注解，该注解标注下的生产者接口类，会被自动实例化成bean托管到spring容器中，业务代码中只需要通过@Resource注解进行引入即可使用目标生产者的消息能力。消费者也是同理。

该`@RocketMqProducer`注解内部的主要参数介绍：

| field        | type    |  default  |  desc  |
| --------   | -----:   | :----: | :----: |
| qualifier        | String      |   Empty String    |  限定值  |
| group        | String      |   Empty String    |  生产者组  |
| sendMsgTimeout        | int      |   10000    |  生产超时时间  |
| vipChannelEnabled        | boolean      |   false    |  开启vip接入方式  |
| txMessage        | boolean      |   false    |  是否为事务型消息  |
| executor        | String      |   Empty String    |  自定义线程池名  |
| listener        | String      |   Empty String    |  回调监听实例名  |
| primary        | boolean      |   true    |  是否优先注入  |
| accessChannel        | AccessChannel枚举类      |  AccessChannel.LOCAL    |  rocketmq实例访问通道类型  |

关于现有的生产者实例如何生产消息，本依赖保留了官方开源版本客户端的编码方式，详见[apache官方文档](http://rocketmq.apache.org/docs/simple-example/)以及本依赖接入[示例](rocketmq-springboot-starter-test)



## 需要优化的点

* @Autowired引入能力
* 生产者与消费者接口类注解参数中，涉及类实例引用的参数优化为强关联
* 性能方面的优化

欢迎志同道合的Coder提交建设性issue！目前本工程的详细使用文档正在进一步推进中，敬请期待！另外欢迎有兴趣的同学直接添加作者微信进行沟通！

![作者微信](imgs/author_wechat_qrcode.png)

