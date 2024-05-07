package com.neo.rabbitmq;

import com.neo.rabbit.fanout.FanoutReceiverA;
import com.neo.rabbit.fanout.FanoutSender;
import com.neo.rabbit.topic.TopicSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FanoutTest {

	@Autowired
	private FanoutSender sender;

	/**
	 * 发生消息后， fanoutReceiverA/B/C 都会自动接受消息 并打印
	 * @throws Exception
	 */
	@Test
	public void fanoutSender() throws Exception {
		sender.send();
	}
}