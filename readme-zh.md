# RocketMQ Example

## 项目说明

本项目演示如何使用 RocketMQ  集成 Spring Cloud Alibaba 应用消息的发送和消费还有分布式事务的处理。
如果大家有一些建议与调整，欢迎在这里进行更改说明。

[RocketMQ]是一款开源的分布式消息系统，基于高可用分布式集群技术，提供低延时的、高可靠的消息发布与订阅服务。


我们先了解一下 Spring Cloud Stream。

这是官方对 Spring Cloud Stream 的一段介绍：

Spring Cloud Stream 是一个用于构建基于消息的微服务应用框架。它基于 SpringBoot 来创建具有生产级别的单机 Spring 应用，并且使用 `Spring Integration` 与 Broker 进行连接。
 
Spring Cloud Stream 提供了消息中间件配置的统一抽象，推出了 publish-subscribe、consumer groups、partition 这些统一的概念。

Spring Cloud Stream 内部有两个概念：Binder 和 Binding。

* Binder: 跟外部消息中间件集成的组件，用来创建 Binding，各消息中间件都有自己的 Binder 实现。

比如 `Kafka` 的实现 `KafkaMessageChannelBinder`，`RabbitMQ` 的实现 `RabbitMessageChannelBinder` 以及 `RocketMQ` 的实现 `RocketMQMessageChannelBinder`。

* Binding: 包括 Input Binding 和 Output Binding。

Binding 在消息中间件与应用程序提供的 Provider 和 Consumer 之间提供了一个桥梁，实现了开发者只需使用应用程序的 Provider 或 Consumer 生产或消费数据即可，屏蔽了开发者与底层消息中间件的接触。


## 示例

详见实例代码
包括消息的发送和消费，相关配置的说明，消息异常的处理，分布式事务的示例。

## Endpoint 信息查看

Spring Boot 应用支持通过 Endpoint 来暴露相关信息，RocketMQ Stream Starter 也支持这一点。

在使用之前需要在 Maven 中添加 `spring-boot-starter-actuator`依赖，并在配置中允许 Endpoints 的访问。
* Spring Boot 1.x 中添加配置 `management.security.enabled=false`
* Spring Boot 2.x 中添加配置 `management.endpoints.web.exposure.include=*`

Spring Boot 1.x 可以通过访问 http://127.0.0.1:18083/rocketmq_binder 来查看 RocketMQ Binder Endpoint 的信息。
Spring Boot 2.x 可以通过访问 http://127.0.0.1:28081/actuator/rocketmq-binder 来访问。

这里会统计消息最后一次发送的数据，消息发送成功或失败的次数，消息消费成功或失败的次数等数据。

```json
{
	"runtime": {
		"lastSend.timestamp": 1542786623915
	},
	"metrics": {
		"scs-rocketmq.consumer.test-topic.totalConsumed": {
			"count": 11
		},
		"scs-rocketmq.consumer.test-topic.totalConsumedFailures": {
			"count": 0
		},
		"scs-rocketmq.producer.test-topic.totalSentFailures": {
			"count": 0
		},
		"scs-rocketmq.consumer.test-topic.consumedPerSecond": {
			"count": 11,
			"fifteenMinuteRate": 0.012163847780107841,
			"fiveMinuteRate": 0.03614605351360527,
			"meanRate": 0.3493213353657594,
			"oneMinuteRate": 0.17099243039490175
		},
		"scs-rocketmq.producer.test-topic.totalSent": {
			"count": 5
		},
		"scs-rocketmq.producer.test-topic.sentPerSecond": {
			"count": 5,
			"fifteenMinuteRate": 0.005540151995103271,
			"fiveMinuteRate": 0.01652854617838251,
			"meanRate": 0.10697493212602836,
			"oneMinuteRate": 0.07995558537067671
		},
		"scs-rocketmq.producer.test-topic.sentFailuresPerSecond": {
			"count": 0,
			"fifteenMinuteRate": 0.0,
			"fiveMinuteRate": 0.0,
			"meanRate": 0.0,
			"oneMinuteRate": 0.0
		},
		"scs-rocketmq.consumer.test-topic.consumedFailuresPerSecond": {
			"count": 0,
			"fifteenMinuteRate": 0.0,
			"fiveMinuteRate": 0.0,
			"meanRate": 0.0,
			"oneMinuteRate": 0.0
		}
	}
}
```

注意：要想查看统计数据需要在pom里加上 [metrics-core依赖](https://mvnrepository.com/artifact/io.dropwizard.metrics/metrics-core)。如若不加，endpoint 将会显示 warning 信息而不会显示统计信息：

```json
{
    "warning": "please add metrics-core dependency, we use it for metrics"
}
```

