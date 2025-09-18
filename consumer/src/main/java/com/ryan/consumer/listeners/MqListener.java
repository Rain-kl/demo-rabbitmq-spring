package com.ryan.consumer.listeners;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MqListener {


    /**
     * 如果 exchange-queue 绑定关系存在, 则可以直接监听队列
     * 代表情况是使用 config 配置了绑定关系
     *
     * @param message
     */
    @RabbitListener(queues = "hello.queue1")
    public void receiveMessageSimple(String message) {
        System.out.println("C1-Received message: " + message);
    }

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
//        throw new RuntimeException(message);
    }

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(name = "object.queue2", durable = "true"),
                    exchange = @Exchange(name = "object.fanout", type = ExchangeTypes.FANOUT)
            )
    )
    public void receiveObject(Map<String, Object> message) {
        System.out.println("Object-Received message: " + message);
//        throw new RuntimeException(message);
    }
}
