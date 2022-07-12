package com.lc.util;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author kaho
 * @description rabbitmq的工具类
 * @date 2022/7/12 14:50
 */
public class rabbitmqUtil {
    public static Channel getChannel() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();

        factory.setUsername("kaho");

        factory.setPassword("000000");

        factory.setHost("");

        Connection conn = factory.newConnection();

        return conn.createChannel();
    }
}
