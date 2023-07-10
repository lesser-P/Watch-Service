package com.beyond.watchservice.utils;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqUtils {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    public void sendMessage(String message,String queueName){
        rabbitTemplate.convertAndSend(message,queueName);
    }
}

