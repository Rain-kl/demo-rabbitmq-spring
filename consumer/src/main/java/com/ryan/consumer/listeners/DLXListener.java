package com.ryan.consumer.listeners;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class DLXListener {

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(name = "dlx.queue", durable = "true"),
                    exchange = @Exchange(
                            name = "dlx.fanout",
                            type = ExchangeTypes.FANOUT
                    )
            )
    )
    public void dlxListener(String message) {
        System.out.println("DLX-Received message: " + message);
        // 这里可以添加处理逻辑，比如记录日志、发送通知等
        // 注意：如果需要重试或其他处理，可以根据业务需求进行相应的操作
        // throw new RuntimeException("Simulated exception for DLX testing");
    }
}

