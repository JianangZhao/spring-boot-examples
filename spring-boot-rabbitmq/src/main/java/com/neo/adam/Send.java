package com.neo.adam;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

@Service
public class Send {
    private final static String QUEUE_NAME = "hello2";

    public static void main(String[] args) throws IOException {
        send();
    }

    public static void send() throws IOException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setVirtualHost("/");

        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        try(Connection connection = connectionFactory.newConnection()){
            //The connection abstracts the socket connection,
            // and takes care of protocol version negotiation and authentication and so on for us.
            Channel channel = connection.createChannel();

            Map<String, Object> args = new HashMap<>();
            //设置队列的过期时间
            args.put("x-message-ttl", 10000);

            channel.queueDeclare(QUEUE_NAME, true, false, false, args);
            String message = "Hello World!";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println("[x] Send '" + message + "'");
        }catch (TimeoutException | IOException e) {
            throw new RuntimeException(e);
        }

    }
}
