package com.lc.workqueue;

import com.lc.util.RabbitMqUtil;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;

/**
 * @author kaho
 * @description 工作线程worker01
 * @date 2022/7/12 15:13
 */
public class Worker01 {
    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) {
        try {
            Channel channel = RabbitMqUtil.getChannel();

            //消息的接收
            /**
             * 消费者接收消息
             * 1消费那个队列
             * 2消费成功之后是否要自动应答，
             * 3消费者未成功消费的回调
             * 4消费者取消消费的回调
             */
            channel.basicConsume(QUEUE_NAME, true, (consumerTag, message) -> {
                System.out.println("接收到的消息" + Arrays.toString(message.getBody()));
            }, (consumerTag) -> {
                System.out.println(consumerTag + "消费者取消消费");
            });
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }

        System.out.println("w1等待接受消息......");
    }
}
