package com.ryan.consumer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "spring.rabbitmq.listener.simple.retry", name = "enabled", havingValue = "true")
public class ErrorMQHandlerConfig {

    /**
     * 配置一个DirectExchange，用于处理错误消息。
     *
     * @return
     */
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("error.direct");
    }

    @Bean
    public Queue errorQueue() {
        return new Queue("error.queue", true);
    }

    @Bean
    public Binding errorBinding(DirectExchange directExchange, Queue errorQueue) {
        return BindingBuilder.bind(errorQueue)
                .to(directExchange)
                .with("error");
    }


    /**
     *
     * 配置消息恢复器，当消息处理失败且重试次数耗尽后，将消息重新发布到错误队列。
     *
     * @param rabbitTemplate RabbitMQ模板，用于重新发布消息
     * @return MessageRecoverer 消息恢复器实例
     * @return
     */
    @Bean
    public MessageRecoverer messageRecoverer(RabbitTemplate rabbitTemplate) {
        return new RepublishMessageRecoverer(rabbitTemplate,
                "error.direct", // 错误消息的交换机
                "error"  //key 错误消息的路由键
        );
    }
}
