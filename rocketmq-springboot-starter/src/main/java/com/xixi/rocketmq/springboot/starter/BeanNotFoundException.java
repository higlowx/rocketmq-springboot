package com.xixi.rocketmq.springboot.starter;

import org.springframework.beans.BeansException;

/**
 * @author Chris.Li
 * @desc
 * @date 2020/3/27
 */

public class BeanNotFoundException extends BeansException {


    public BeanNotFoundException(String msg) {
        super(msg);
    }

    public BeanNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
