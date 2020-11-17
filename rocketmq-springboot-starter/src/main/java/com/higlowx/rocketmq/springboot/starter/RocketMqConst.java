package com.higlowx.rocketmq.springboot.starter;

/**
 * @author Dylan.Li
 * @since 1.0
 * @date 2019/11/13
 */

class RocketMqConst {

    static final String ANNOTATION_PROPERTY_VIP_VHANNEL_ENABLED = "vipChannelEnabled";
    static final String ANNOTATION_PROPERTY_GROUP = "group";
    static final String ANNOTATION_PROPERTY_SEND_MSG_TIMEOUT = "sendMsgTimeout";
    static final String ANNOTATION_PROPERTY_NAMESRV = "nameServer";
    static final String ANNOTATION_PROPERTY_TOPIC = "topic";
    static final String ANNOTATION_PROPERTY_TAG = "tag";
    static final String ANNOTATION_PROPERTY_AUALIFIER = "qualifier";
    static final String ANNOTATION_PROPERTY_PRIMARY = "primary";
    static final String ANNOTATION_PROPERTY_IS_TX_MESSAGE = "txMessage";
    static final String ANNOTATION_PROPERTY_EXECUTOR = "executor";
    static final String ANNOTATION_PROPERTY_LISTENER = "listener";
    static final String ANNOTATION_PROPERTY_CONSUME_TYPE = "consumeType";
    static final String ANNOTATION_PROPERTY_MSG_MODEL = "msgModel";
    static final String ANNOTATION_PROPERTY_CONSUME_FROM = "consumeFrom";
    static final String ANNOTATION_PROPERTY_ACCESS_CHANNEL = "accessChannel";

    static final String PROPERTY_VIP_VHANNEL_ENABLED = ANNOTATION_PROPERTY_VIP_VHANNEL_ENABLED;
    static final String PROPERTY_GROUP = ANNOTATION_PROPERTY_GROUP;
    static final String PROPERTY_SEND_MSG_TIMEOUT = ANNOTATION_PROPERTY_SEND_MSG_TIMEOUT;
    static final String PROPERTY_NAMESRVADDR = "namesrvAddr";
    static final String PROPERTY_PRODUCER = "producer";
    static final String PROPERTY_TYPE = "type";
    static final String PROPERTY_IS_TX_MESSAGE = ANNOTATION_PROPERTY_IS_TX_MESSAGE;
    static final String PROPERTY_EXECUTOR = ANNOTATION_PROPERTY_EXECUTOR;
    static final String PROPERTY_LISTENER = ANNOTATION_PROPERTY_LISTENER;
    static final String PROPERTY_TOPIC = ANNOTATION_PROPERTY_TOPIC;
    static final String PROPERTY_TAG = ANNOTATION_PROPERTY_TAG;
    static final String PROPERTY_CONSUME_TYPE = ANNOTATION_PROPERTY_CONSUME_TYPE;
    static final String PROPERTY_MSG_MODEL = ANNOTATION_PROPERTY_MSG_MODEL;
    static final String PROPERTY_CONSUME_FROM = ANNOTATION_PROPERTY_CONSUME_FROM;
    static final String PROPERTY_ACCESS_CHANNEL = ANNOTATION_PROPERTY_ACCESS_CHANNEL;

    static final int DEFAULT_SEND_MSG_TIMEOUT = 10000;
    static final boolean DEFAULT_VIP_CHANNEL_ENABLED = false;

    static final String BASE_PRODUCER_CLASSNAME = "com.higlowx.rocketmq.springboot.starter.BaseRocketMqProducer";
    static final String BASE_CONSUMER_CLASSNAME = "com.higlowx.rocketmq.springboot.starter.BaseRocketMqConsumer";

    @Deprecated
    static final String UNIFIED_CONFIG_BEANNAME = "rocketMqUnifiedProperties";
    static final String UNIFIED_CONFIG_FILE_PREFIX = "rocketmq";


    /**
     * 设置消息的定时投递时间（绝对时间). <p>例1: 延迟投递, 延迟3s投递, 设置为: System.currentTimeMillis() + 3000; <p>例2: 定时投递,
     * 2016-02-01 11:30:00投递, 设置为: new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2016-02-01
     * 11:30:00").getTime()
     */
    static final String USER_PROPERTY_STARTDELIVERTIME = "__STARTDELIVERTIME";
    static final String USER_PROPERTY_SHARDINGKEY = "__SHARDINGKEY";


}
