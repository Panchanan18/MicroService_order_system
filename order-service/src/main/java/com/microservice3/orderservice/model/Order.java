package com.microservice3.orderservice.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "order")
public class Order {
    @Id
    private String id;
    private String orderNumber;
    private List<OrderLineItems> orderLineItemsList;
}
