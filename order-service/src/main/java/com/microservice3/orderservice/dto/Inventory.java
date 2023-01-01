package com.microservice3.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Inventory {
    private String id;
    private String skuCode;
    private  long quantity;

    public Inventory(String skuCode, long l) {
    }
}
