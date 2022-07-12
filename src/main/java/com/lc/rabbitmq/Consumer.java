package com.lc.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;

/**
 * @author kaho
 * @description 消费者接收消息
 * @date 2022/7/12 13:59
 */
public class Consumer {
    //队列的名称
    private static final String QUEUE_NAME = "hello";
    private static Connection conn;
    private static Channel channel;


    //接收消息
    public static void main(String[] args) {
        try {
            //创建连接工厂
            ConnectionFactory connectionFactory = new ConnectionFactory();

            connectionFactory.setHost("");

            connectionFactory.setUsername("kaho");

            connectionFactory.setPassword("000000");

            conn = connectionFactory.newConnection();

            //创建信道
            channel = conn.createChannel();

            /**
             * 消费者接收消息
             * 1消费那个队列
             * 2消费成功之后是否要自动应答，
             * 3消费者未成功消费的回调
             * 4消费者取消消费的回调
             */
            channel.basicConsume(QUEUE_NAME, true, ((consumerTag, message) -> {
                System.out.println("消费者未成功消费");
                System.out.println(Arrays.toString(message.getBody()));

            }), (consumerTag) -> {
                System.out.println("消费者取消消费");
            });
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (channel != null) {
                try {
                    channel.close();
                } catch (IOException | TimeoutException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}
