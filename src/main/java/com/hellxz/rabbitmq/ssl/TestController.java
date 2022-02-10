package com.hellxz.rabbitmq.ssl;

import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    RabbitMQSenderService rabbitMQSenderService;
    
    @GetMapping("/test")
    public void sendMsg() {
        Message msg = new Message("hello world".getBytes());
        try {
            rabbitMQSenderService.send(RabbitFanoutExchangeConfig.FANOUT_EXCHANGE,
                    RabbitFanoutExchangeConfig.FANOUT_QUEUE1, msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
