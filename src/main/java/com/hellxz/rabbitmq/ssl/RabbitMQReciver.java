package com.hellxz.rabbitmq.ssl;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
class RabbitMQReciver {

    @RabbitListener(queues = RabbitFanoutExchangeConfig.FANOUT_QUEUE1)
    public void reciveLogAll(String msg) throws Exception {
        System.out.println("received msg:" + msg);
    }
}
