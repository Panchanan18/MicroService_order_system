package com.microservice3.notificationservice.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

import java.io.IOException;
import java.util.Map;

public class OrderPlacedEventSerDes implements Serializer<OrderPlacedEvent>, Deserializer<OrderPlacedEvent> {
    public static final ObjectMapper mapper = JsonMapper.builder()
            .findAndAddModules()
            .build();
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Serializer.super.configure(configs, isKey);
    }

    @Override
    public byte[] serialize(String topic, OrderPlacedEvent orderPlacedEvent) {


        try {
            return mapper.writeValueAsBytes(orderPlacedEvent);
        } catch (JsonProcessingException e) {
            throw new SerializationException(e);
        }

    }

    @Override
    public OrderPlacedEvent deserialize(String topic, byte[] data) {
        try {
            return mapper.readValue(data, OrderPlacedEvent.class);
        } catch (IOException e) {
            throw new SerializationException(e);
        }
    }


    @Override
    public void close() {
        Serializer.super.close();
    }
}
