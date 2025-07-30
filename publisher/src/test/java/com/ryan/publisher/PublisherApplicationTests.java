package com.ryan.publisher;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PublisherApplicationTests {

	@Autowired
	RabbitTemplate rabbitTemplate;

	@Test
	void contextLoads() {
		String key="hello.queue1";
		for(int i = 0; i < 50; i++) {
			String message = "Hello World_" + i;
			rabbitTemplate.convertAndSend(key, message);
			System.out.println("Sent message: " + message);
		}
	}

}
