package com.dylanlee.rocketmq.springboot.starter;

import org.springframework.beans.BeansException;

/**
 * @author Dylan.Lee
 * @since 1.0
 * @date 2020/3/27
 */
@Deprecated
public class BeanNotFoundException extends BeansException {


    public BeanNotFoundException(String msg) {
        super(msg);
    }

    public BeanNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
