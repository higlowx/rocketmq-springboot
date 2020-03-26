package com.xixi.rocketmq.springboot.starter;

import com.alibaba.fastjson.JSON;
import com.xixi.rocketmq.springboot.starter.test.Producer1;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Chris.Li
 * @desc
 * @date 2020/1/9
 */
@RestController
@RequestMapping("/producer")
public class ProducerRest {

    public static final String SECONDS_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Resource
    Producer1 producer1;

    @PostMapping(value = "/send")
    public String send(String type, String time) throws Exception {
        Map<Integer, SendResult> resultMap = new HashMap<>(10);
        if ("timing".equals(type)) {
            for (int i = 0; i < 10; i++) {
                Message message = new Message();
                message.setTopic("msgcenter-timing");
                message.setKeys("17862808091");
                message.setTags("sms");
                message.setBody("你好".getBytes());
                DateTimeFormatter formatter = DateTimeFormat.forPattern(SECONDS_DATE_FORMAT);
                Date date = formatter.parseDateTime(time).toDate();
                MessageWrapper.toTimer(message, date);
                SendResult result = producer1.send(message);
                resultMap.put(i, result);
            }
        } else if ("normal".equals(type)) {
            for (int i = 0; i < 10; i++) {
                Message message = new Message();
                message.setTopic("msgcenter-normal");
                message.setKeys("17862808091");
                message.setTags("sms");
                message.setBody("你好".getBytes());
                SendResult result = producer1.send(message);
                resultMap.put(i, result);
            }
        }
        return JSON.toJSONString(resultMap);
    }
}
