package com.mtech.customerservice.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mtech.customerservice.entity.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CustomerEventPublisher {

    private static final Logger logger = LoggerFactory.getLogger(CustomerEventPublisher.class);

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routingkey}")
    private String routingKey;

    public CustomerEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishCustomerCreatedEvent(Customer customer) {
        logger.info("Publishing event to RabbitMQ. Exchange: {}, RoutingKey: {}, Payload: {}", exchange, routingKey, customer);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        try {
            // Convert full entity to JSON
            String json = objectMapper.writeValueAsString(customer);

            rabbitTemplate.convertAndSend(exchange, routingKey, json, message -> {
                // Remove __TypeId__ to avoid consumer needing the class
                message.getMessageProperties().getHeaders().remove("__TypeId__");
                return message;
            });
        } catch (JsonProcessingException e) {
            logger.error("Failed to serialize Customer entity", e);
        }
    }

    public void publishCustomerUpdatedEvent(Customer customer) {
        logger.info("Publishing update event to RabbitMQ. Exchange: {}, RoutingKey: {}, Payload: {}", exchange, routingKey, customer);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // handle LocalDateTime
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        try {
            // Convert full entity to JSON
            String json = objectMapper.writeValueAsString(customer);

            rabbitTemplate.convertAndSend(exchange, routingKey, json, message -> {
                // Remove __TypeId__ to avoid consumer needing the class
                message.getMessageProperties().getHeaders().remove("__TypeId__");
                return message;
            });
        } catch (JsonProcessingException e) {
            logger.error("Failed to serialize Customer entity", e);
        }
    }
}
