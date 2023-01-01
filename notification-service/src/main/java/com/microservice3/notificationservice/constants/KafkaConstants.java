package com.microservice3.notificationservice.constants;

import org.springframework.stereotype.Component;

@Component
public  class KafkaConstants {

    public static final String TOPIC="notification_update";

    public static final String GROUP_ID="group-notification";
    public static final String HOST="localhost:9092";
}