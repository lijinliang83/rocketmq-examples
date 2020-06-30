package com.dawn.rocketmq.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

/**
 * @Description: 生产者启动类
 * 做一些说明：RocketMQ建议我们一个应用使用一个Topic，不同的消息类型通过Tag来区分
 * @Author: lijinliang
 * @Date: 2020/6/28 15:25
 */
@SpringBootApplication
@EnableBinding({MySource.class})
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
