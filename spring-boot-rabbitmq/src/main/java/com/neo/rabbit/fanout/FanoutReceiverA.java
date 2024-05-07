package com.neo.rabbit.fanout;

import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

@Component
//@RabbitListener(queues = "fanout.A")
@RabbitListener(bindings=@QueueBinding(value=@Queue(value="fanout.A",durable="true"),
        exchange=@Exchange(name="fanoutExchange",type="fanout"), key="red"))
public class FanoutReceiverA {
    @RabbitHandler
    public void process(String message) {
        System.out.println("fanout Receiver A: " + message);
    }

}
