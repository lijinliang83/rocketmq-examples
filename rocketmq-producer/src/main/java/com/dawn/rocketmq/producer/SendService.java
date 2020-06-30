package com.dawn.rocketmq.producer;

import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

/**
 * @Description: 发送消息
 * @Author: lijinliang
 * @Date: 2020/6/28 15:27
 */
@Service
public class SendService {

    @Autowired
    private MySource source;

    /**
     * 发送字符串
     *
     * @param msg
     */
    public void send(String msg) {
        Message message = MessageBuilder.withPayload(msg).build();
        source.output1().send(message);
    }

    /**
     * 发送带tag的字符串
     *
     * @param msg
     */
    public void sendWithTags(String msg, String tag) {
        Message message = MessageBuilder.withPayload(msg)
                .setHeader(RocketMQHeaders.TAGS, tag)
                .build();
        source.output1().send(message);
    }

    /**
     * 发送带消息种类tag和消息索引key的字符串消息
     *
     * @param msg
     * @param tag
     */
    public void sendWithTags(String msg, String tag, String key) {
        Message message = MessageBuilder.withPayload(msg)
                .setHeader(RocketMQHeaders.TAGS, tag)
                .setHeader(RocketMQHeaders.KEYS, key)
                .build();
        source.output1().send(message);
    }

    /**
     * 发送带tag的对象
     *
     * @param msg
     * @param tag
     * @param <T>
     */
    public <T> void sendObject(T msg, String tag) {
        Message message = MessageBuilder.withPayload(msg)
                .setHeader(RocketMQHeaders.TAGS, tag)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                .build();
        source.output2().send(message);
    }

}
