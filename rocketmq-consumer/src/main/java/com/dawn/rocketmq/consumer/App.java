package com.dawn.rocketmq.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

/**
 * @Description: 消费者启动类
 * @Author: lijinliang
 * @Date: 2020/6/28 15:25
 */
@SpringBootApplication
@EnableBinding({MyConsumer.class})
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
