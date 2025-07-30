package com.ryan.publisher;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PublisherApplicationTests {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    void contextLoads() {
        String key = "hello.queue1";
        for (int i = 0; i < 50; i++) {
            String message = "Hello World_" + i;
            rabbitTemplate.convertAndSend(key, message);
            System.out.println("Sent message: " + message);
        }
    }

    @Test
    void sendToFanout() {
        String exchange = "hello.fanout";
        String message = "Hello World to Exchange";
        for (int i = 0; i < 50; i++) {
            rabbitTemplate.convertAndSend(exchange, "", message + " " + i);
            System.out.println("Sent message to exchange: " + message + " " + i);
        }
    }

    @Test
    void sendToDirect() {
        String exchange = "hello.direct";
        rabbitTemplate.convertAndSend(exchange, "a", "Key A Message");
        rabbitTemplate.convertAndSend(exchange, "b", "Key B Message");
        rabbitTemplate.convertAndSend(exchange, "c", "Key C Message");
        System.out.println("Sent messages to direct exchange with keys 'a' and 'b'");

    }

    @Test
    void testACKCallback() throws InterruptedException {
        CorrelationData cd = new CorrelationData();
        cd.getFuture().whenComplete(
                (result, ex) -> {
                    if (ex != null) {
                        System.err.println("Error sending message: " + ex.getMessage());
                    } else {
                        if (result.isAck()) {
                            System.out.println("Message successfully sent to exchange.");
                        } else {
                            System.err.println("Message failed to be sent to exchange: " + result.getReason());
                        }
                    }
                }
        );

        String exchange = "hello.direct";
        rabbitTemplate.convertAndSend(exchange, "a", "Key A Message", cd);
        Thread.sleep(50);


    }
}
