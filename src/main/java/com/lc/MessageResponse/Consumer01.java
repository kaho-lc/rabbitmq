package com.lc.MessageResponse;

import com.lc.util.RabbitMqUtil;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;

/**
 * @author kaho
 * @description
 * @date 2022/7/12 16:37
 */
public class Consumer01 {
    private static final String TASK_QUEUE_NAME = "ack_queue";

    public static void main(String[] args) {
        try {
            Channel channel = RabbitMqUtil.getChannel();
            System.out.println("消费者1等待接收消息处理");

            //采用手动应答
            channel.basicConsume(TASK_QUEUE_NAME, false, (consumerTag, message) -> {
                //沉睡1秒钟
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("接收到的消息为: " + Arrays.toString(message.getBody()));

                //进行手动应答
                /**
                 * 1表示消息的标记 tag  从消息中拿到消息的标签tag
                 * 2是否批量应答 false表示不批量应答
                 */

                channel.basicAck(message.getEnvelope().getDeliveryTag(), false);

            }, (consumerTag) -> {
                System.out.println("消费者取消消费接口回调");
            });
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}
