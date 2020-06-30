package com.dawn.rocketmq.producer;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @Description: 生产者消息
 * @Author: lijinliang
 * @Date: 2020/6/28 15:25
 */
public interface MySource {

    @Output("output1")
    MessageChannel output1();

    @Output("output2")
    MessageChannel output2();
}
