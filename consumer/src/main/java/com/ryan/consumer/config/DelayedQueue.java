package com.ryan.consumer.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class DelayedQueue {

    @Bean
    public Queue configDelayedQueue() {
        return QueueBuilder.durable("delayed.queue") // 创建一个持久化的队列
                .withArguments(Map.of("x-dead-letter-exchange", "dlx.fanout", // 设置死信交换机
                        "x-dead-letter-routing-key", "error",
                        "x-message-ttl", 10000)
                )
                .build();
    }

    @Bean
    public FanoutExchange dlxExchange() {
        return ExchangeBuilder.fanoutExchange("delayed.fanout")
                .durable(true)
                .build();

    }

    @Bean
    public Binding bindingDelayedQueue(Queue configDelayedQueue, FanoutExchange dlxExchange) {
        return BindingBuilder.bind(configDelayedQueue).to(dlxExchange);
    }

}
