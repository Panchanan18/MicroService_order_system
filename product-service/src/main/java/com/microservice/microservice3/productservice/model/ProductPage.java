package com.microservice.microservice3.productservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductPage {
    private List<Product> productList;
    private int pageNumber;
    private int noOfElements;
    private Long totalElements;
    private int totalPages;
    private boolean lastPage;
}
