package com.dylanlee.rocketmq.springboot.starter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Dylan.Lee
 * @since 1.0
 * @date 2020/1/8
 */
@Component(RocketMqConst.UNIFIED_CONFIG_BEANNAME)
@ConfigurationProperties(prefix = RocketMqConst.UNIFIED_CONFIG_FILE_PREFIX, ignoreUnknownFields = true)
public class RocketMqUnifiedProperties {

    private String namesrvAddr;
    private String accessKey;
    private String accessSecret;

    public String getNamesrvAddr() {
        return namesrvAddr;
    }

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getAccessSecret() {
        return accessSecret;
    }

    public void setAccessSecret(String accessSecret) {
        this.accessSecret = accessSecret;
    }

    @Override
    public String toString() {
        return "RocketMqUnifiedProperties{" +
                "namesrvAddr='" + namesrvAddr + '\'' +
                ", accessKey='" + accessKey + '\'' +
                ", accessSecret='" + accessSecret + '\'' +
                '}';
    }
}
