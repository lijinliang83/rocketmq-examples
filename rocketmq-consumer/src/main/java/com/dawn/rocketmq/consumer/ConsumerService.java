package com.dawn.rocketmq.consumer;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.stereotype.Service;

/**
 * @Description: 消费消息
 * @Author: lijinliang
 * @Date: 2020/6/28 16:57
 */
@Service
public class ConsumerService {

    @StreamListener("input1")
    public void receiveInput1(String receiveMsg) {
        System.out.println("input1 接收消息: " + receiveMsg);
    }

    @StreamListener("input2")
    public void receiveInput2(@Payload TestDto dto) {
        System.out.println("input2 接收消息: " + dto.getName());
    }

    @ServiceActivator(inputChannel = "test-topic-one.test-consumer-group-1.errors")
    public void handleConsumeUserError(ErrorMessage message) {
        System.out.println("收到input1处理失败的消息："+ message.getPayload());
    }

    /**
     * 统一的入口来处理所有的消息消费异常
     * @param message
     */
    @StreamListener("errorChannel")
    public void handleErrors(ErrorMessage message) {
        System.out.println("默认的消息失败处理器收到处理失败的消息");
        System.out.println(message.getOriginalMessage());
        System.out.println(message.getHeaders());
    }
}
