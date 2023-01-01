package com.microservice3.notificationservice.service;

import com.microservice3.notificationservice.constants.KafkaConstants;
import com.microservice3.notificationservice.event.OrderPlacedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Slf4j
public class ConsumerService {
    @Autowired
    private KafkaTemplate kafkaTemplate;

    @KafkaListener(topics = KafkaConstants.TOPIC,groupId = KafkaConstants.GROUP_ID)
    public void consume(OrderPlacedEvent orderPlacedEvent){
        log.info("Notification received for order no "+orderPlacedEvent.getOrderNumber());
    }

}
