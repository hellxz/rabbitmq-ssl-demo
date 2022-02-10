package com.hellxz.rabbitmq.ssl;

import java.util.UUID;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQSenderService {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(String exchange, String routingkey, Message message) {

        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        System.out.println("start send msg : " + message);
        rabbitTemplate.convertAndSend(exchange, routingkey, message, correlationId);
        System.out.println("end send msg : " + message);
    }
}
