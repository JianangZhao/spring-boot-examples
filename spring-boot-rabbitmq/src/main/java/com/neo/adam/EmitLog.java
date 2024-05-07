package com.neo.adam;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * 添加exchange 实现发布订阅功能
 */
public class EmitLog {
    private final static String EXCHANGE_NAME = "logs";

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
//        connectionFactory.setVirtualHost("/test");
        try(Connection connection = connectionFactory.newConnection()){
            //The connection abstracts the socket connection,
            // and takes care of protocol version negotiation and authentication and so on for us.
            Channel channel = connection.createChannel();
            channel.exchangeDeclare("logs", "direct");
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()){
                String message = scanner.nextLine();
                channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
                System.out.println(" [x] Sent '" + message + "'");

            }

        }catch (TimeoutException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
