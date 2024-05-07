package com.neo.adam;

import com.rabbitmq.client.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

@Service
public class Recv {

    private final static String QUEUE_NAME = "hello2";

    public static void main(String[] args) throws IOException, TimeoutException {
        receiver();
    }
    public static void receiver() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setVirtualHost("/");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        Map<String, Object> args = new HashMap<>();
        //设置队列的过期时间
        args.put("x-message-ttl", 10000);
        channel.queueDeclare(QUEUE_NAME, true, false, false, args);
//        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag ->{});
    }
}
