package com.dawn.rocketmq.producer;

import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

/**
 * @Description: 对需要使用分布式事务的消息发送接口监听
 * ◆  Commit 提交事务消息,消费者可以消费此消息
 * ◆  Rollback 回滚事务消息, broker会删除该消息,消费者不能消费
 * ◆  UNKNOWN broker需要回查确认该消息的状态
 * @Author: lijinliang
 * @Date: 2020/6/29 15:41
 */
@RocketMQTransactionListener(txProducerGroup = "test-producer-group-2",corePoolSize = 5, maximumPoolSize = 10)
public class TransactionListener implements RocketMQLocalTransactionListener {


    /**
     * 执行本地事务
     * @param message
     * @param o
     * @return
     */
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        //收到MQ-Server返回的消息，说明之前发的事务半消息成功了
        MessageHeaders headers = message.getHeaders();
        String tag= (String) headers.get(RocketMQHeaders.PREFIX+RocketMQHeaders.TAGS);
        System.out.println(tag);
        System.out.println(new String((byte[]) message.getPayload()));

        //执行本地事务
        try{
            //这里执行SQL

            //如果事务执行没有问题，二次确认提交给MQ-Server
            return RocketMQLocalTransactionState.COMMIT;
        }catch(Exception e){
            return RocketMQLocalTransactionState.ROLLBACK;
        }

    }

    /**
     * 执行本地事务回查，回查间隔时间差不多一分钟
     * 没收到发送者发送的二次确认消息
     * 或者当状态为 UNKNOW 执行这个方法
     * @param message
     * @return
     */
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        //如果MQ-server没有收到之前二次确认的消息，执行回查
        System.out.println("执行本地回查");

        //执行自己的数据库查询语句，检查事务是否执行成功

        //如果得出的结果是事务执行成功了，将消息提交给MQ-Server
        return RocketMQLocalTransactionState.COMMIT;

        //如果得出的结果是事务没执行成功，将消息提交给MQ-Serve
        //return RocketMQLocalTransactionState.ROLLBACK;

    }
}
