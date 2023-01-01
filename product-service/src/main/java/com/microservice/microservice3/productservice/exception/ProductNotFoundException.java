package com.microservice.microservice3.productservice.exception;


public class ProductNotFoundException extends  Exception {
    public ProductNotFoundException() {
        super();
    }

    public ProductNotFoundException(String message) {
        super(message);
    }

}
