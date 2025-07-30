package com.ryan.publisher.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class MQConfig implements ApplicationContextAware {
    /**
     * 消息被正确投入队列后，RabbitTemplate会调用这个回调方法。
     *
     * @param applicationContext 应用上下文
     * @throws BeansException 如果在设置应用上下文时发生错误
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        RabbitTemplate rabbitTemplate = applicationContext.getBean(RabbitTemplate.class);
        rabbitTemplate.setReturnsCallback(returned -> {
            String message = new String(returned.getMessage().getBody());
            String exchange = returned.getExchange();
            String routingKey = returned.getRoutingKey();
            log.error("Message returned: {}, Exchange: {}, Routing Key: {}", message, exchange, routingKey);
        });
    }
}
