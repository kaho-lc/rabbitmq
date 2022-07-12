package com.lc.MessageResponse;

import com.lc.util.RabbitMqUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Delivery;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * @author kaho
 * @description consumer01 消息在手动应答时是不丢失的，并且会放回队列中重新消费
 * @date 2022/7/12 16:25
 */
public class Producer01 {
    private static final String TASK_QUEUE_NAME = "ack_queue";

    public static void main(String[] args) {
        try {
            Channel channel = RabbitMqUtil.getChannel();

            //声明队列
            channel.queueDeclare(TASK_QUEUE_NAME, false, false, false, null);

            //从控制台中输入信息
            Scanner scanner = new Scanner(System.in);

            while (scanner.hasNext()) {
                String nextMessage = scanner.next();
                channel.basicPublish("", TASK_QUEUE_NAME, null, nextMessage.getBytes(StandardCharsets.UTF_8));
                System.out.println("生产者发送消息: " + nextMessage);
            }
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }

    }
}
