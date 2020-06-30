package com.dawn.rocketmq.consumer;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @Description: 消费者消息
 * @Author: lijinliang
 * @Date: 2020/6/28 16:55
 */
public interface MyConsumer {

    @Input("input1")
    SubscribableChannel input1();

    @Input("input2")
    SubscribableChannel input2();
}
