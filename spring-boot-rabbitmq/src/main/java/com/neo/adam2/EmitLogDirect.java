package com.neo.adam2;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class EmitLogDirect {
    public static final String EXCHANGE_NAME = "direct_logs";
    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setVirtualHost("/");
        try (Connection connection = factory.newConnection()) {
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");
            String severity = getSeverity(args);
            String message = getMessage(args);
            channel.basicPublish(EXCHANGE_NAME,severity,null,message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + severity + "':'" + message + "'");
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getMessage(String[] args) {
        if (args.length < 1){
            return "info";
        }
        return args[0];
    }

    private static String getSeverity(String[] args) {
        if (args.length < 2){
            return "hello world";
        }
        return joinStrings(args, " ", 1);
    }

    private static String joinStrings(String[] args, String space, int i) {
        int length = args.length;
        if (length == 0) return "";
        if(length <= i) return  "";
        StringBuilder stringBuilder = new StringBuilder(args[i]);
        for(int j=i+1; j<length; j++) {
            stringBuilder.append(space).append(args[j]);
        }
        return stringBuilder.toString();
    }
}
