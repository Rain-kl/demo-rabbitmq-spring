package com.ryan.consumer.listeners;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MqListener {

    @RabbitListener(queues = "hello.queue1")
    public void receiveMessage(String message) throws InterruptedException {
        System.out.println("Received message: " + message);
        Thread.sleep(50);
    }

    @RabbitListener(queues = "hello.queue1")
    public void receiveMessage2(String message) {
        System.err.println("Received message: " + message);
    }
}
