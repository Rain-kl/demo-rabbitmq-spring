package com.ryan.consumer.listeners;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MqListener {

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(name = "hello.queue1", durable = "true"),
                    exchange = @Exchange(name = "hello.direct", type = ExchangeTypes.DIRECT),
                    key = {"a", "b"}
            )
    )
    public void receiveMessage(String message) throws InterruptedException {
        System.out.println("C1-Received message: " + message);
    }


    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(name = "hello.queue2", durable = "true"),
                    exchange = @Exchange(name = "hello.direct", type = ExchangeTypes.DIRECT),
                    key = {"b", "c"}
            )
    )
    public void receiveMessage2(String message) {
        System.err.println("C2-Received message: " + message);
    }
}
