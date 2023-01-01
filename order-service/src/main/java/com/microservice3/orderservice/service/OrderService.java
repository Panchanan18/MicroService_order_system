package com.microservice3.orderservice.service;

import com.microservice3.orderservice.constants.KafkaConstants;
import com.microservice3.orderservice.dto.Inventory;
import com.microservice3.orderservice.dto.InventoryResponse;
import com.microservice3.orderservice.dto.OrderLineItemsDto;
import com.microservice3.orderservice.dto.OrderRequest;
import com.microservice3.orderservice.event.OrderPlacedEvent;
import com.microservice3.orderservice.model.Order;
import com.microservice3.orderservice.model.OrderLineItems;
import com.microservice3.orderservice.repo.OrderRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class OrderService {
    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private WebClient.Builder webclientBuilder;

    @Autowired
    private KafkaTemplate kafkaTemplate;

    public Order placeOrder(OrderRequest orderRequest) throws IllegalArgumentException {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItemsList = orderRequest.getOrderLineItemsDtoList().stream().
                map(this::mapToDto).collect(Collectors.toList());
        order.setOrderLineItemsList(orderLineItemsList);

        List<String> skuCodes = orderLineItemsList.stream().map(OrderLineItems::getSkuCode).collect(Collectors.toList());

        InventoryResponse[] inventoryResponses = webclientBuilder.build().get()
                .uri("http://INVENTORY-SERVICE/inventory/isInStock",
                        uriBuilder -> uriBuilder.queryParam("skuCodes", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();
        boolean allProductInStock = Arrays.stream(inventoryResponses).allMatch(InventoryResponse::isInStock);
        Order savedOrder;
        if(allProductInStock){
            savedOrder = orderRepo.save(order);
//            List<Inventory> inventoryList = orderLineItemsList.stream().map(orderLineItems ->
//                            new Inventory(orderLineItems.getSkuCode(), -orderLineItems.getQuantity()))
//                    .collect(Collectors.toList());
//            Inventory inventory;
//            for(Inventory inv : inventoryList){
//                inventory = webclientBuilder.build().put()
//                        .uri("http://INVENTORY-SERVICE/inventory/update/delta")
//                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                        .body(BodyInserters.fromValue(inv)).retrieve()
//                        .bodyToMono(Inventory.class)
//                        .block();
//                break;
//            }

//
//            List<Inventory> inventories = inventoryList.stream().map(inventory -> webclientBuilder.build().put()
//                    .uri("http://INVENTORY-SERVICE/inventory/update/delta")
//                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                    .body(BodyInserters.fromValue(inventory)).retrieve()
//                    .bodyToMono(Inventory.class)
//                    .block()).collect(Collectors.toList());


            kafkaTemplate.send(KafkaConstants.TOPIC,new OrderPlacedEvent(order.getOrderNumber()));
            log.info("Order with order number "+order.getOrderNumber()+" is placed.");
            log.info("Message send to notification_update topic");
        }
        else{
            throw new IllegalArgumentException("Product is not available in the stock");
        }

        return savedOrder;
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setId(orderLineItemsDto.getId());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        return orderLineItems;
    }
}
