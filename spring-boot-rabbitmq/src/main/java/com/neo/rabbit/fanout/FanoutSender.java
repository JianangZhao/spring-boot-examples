package com.neo.rabbit.fanout;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FanoutSender {

	@Autowired
	private AmqpTemplate rabbitTemplate;

	public void send() {
		String context = "hi, fanout msg ";
		System.out.println("Sender : " + context);
		// Send message to queue "fanout.exchange" with routing key "red"
		// 配置路由规则red 也即是只有消费端也配置了同样的路由才能消费
		this.rabbitTemplate.convertAndSend("fanoutExchange","red", context);
	}

}