package com.microservice3.orderservice.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class OrderLineItems {
    private Integer id;
    private String skuCode;
    private BigDecimal price;
    private long quantity;
}
