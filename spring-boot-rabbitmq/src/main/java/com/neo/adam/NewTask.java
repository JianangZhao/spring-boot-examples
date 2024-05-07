package com.neo.adam;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Service
public class NewTask {
    private final static String TASK_QUEUE_NAME = "task";

    public void send() throws IOException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setVirtualHost("/");
        try(Connection connection = connectionFactory.newConnection()){
            //The connection abstracts the socket connection,
            // and takes care of protocol version negotiation and authentication and so on for us.
            Channel channel = connection.createChannel();
            channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
            String message = "Hello World!";
            channel.basicPublish("", TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            System.out.println("[x] Send '" + message + "'");
        }catch (TimeoutException e) {
            throw new RuntimeException(e);
        }

    }
}
