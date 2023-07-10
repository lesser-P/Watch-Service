package com.beyond.watchservice.config;

import com.beyond.watchservice.utils.MQCode;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    @Bean("watchExchange")
    public Exchange watchExchange(){
        return ExchangeBuilder.topicExchange(MQCode.EXCHANGE_NAME).durable(true).build();
    }
    @Bean("watchQueue")
    public Queue watchQueue(){
        return QueueBuilder.durable(MQCode.QUEUE_NAME).build();
    }
    @Bean
    public Binding bindingQueueExchange(@Qualifier("watchQueue") Queue queue, @Qualifier("watchExchange") Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(MQCode.ROUT_KEY).noargs();
    }

}

