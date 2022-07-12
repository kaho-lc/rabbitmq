package com.lc.workqueue;

import com.lc.util.RabbitMqUtil;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * @author kaho
 * @description 生产者代码
 * @date 2022/7/12 15:29
 */
public class Task01 {
    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) {
        try {
            Channel channel = RabbitMqUtil.getChannel();
            /**
             * 生成一个队列
             *  1队列的名称
             *  2队列里面的消息是否持久化， 默认情况消息存储在内存中
             *  3该队列是否只供一个消费者进行消费 ， 是否进行消息共享 ， true代表着可以多个消费者消费
             *  4消费者断开连接以后，该队列是否自动删除
             */
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            //从控制台接收消息
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()) {
                String nextMessage = scanner.next();

                /**
                 * 1发送到那个交换机
                 * 2路由的key值是那个 ， 本次是队列的名称
                 * 3其他参数信息
                 * 4发送消息的消息体
                 */
                channel.basicPublish("", QUEUE_NAME, null, nextMessage.getBytes());
            }
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }

    }
}
