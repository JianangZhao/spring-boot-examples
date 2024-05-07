package com.neo.adam2;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class ReceiveLogsDirect {
    public static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setVirtualHost("/");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        String queueName = channel.queueDeclare().getQueue();
//        if (argv.length < 1) {
//            System.err.println("Usage: ReceiveLogsDirect [info] [warning] [error]");
//            System.exit(1);
//        }

        channel.queueBind(queueName, EXCHANGE_NAME, "info");
        channel.queueBind(queueName, EXCHANGE_NAME, "warn");
        channel.queueBind(queueName, EXCHANGE_NAME, "error");


//        for (String severity : argv) {
//            // Bind the queue to the exchange with a routing key of the severity
//         channel.queueBind(queueName, EXCHANGE_NAME, severity);
//        }
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, message) -> {
            String string = new String(message.getBody(), "UTF-8");
            System.out.println(" [x] Received '" +
                    message.getEnvelope().getRoutingKey() + "':'" + string + "'");
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {});
    }
}