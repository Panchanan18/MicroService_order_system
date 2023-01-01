package com.microservice.microservice3.productservice.advice;

import com.microservice.microservice3.productservice.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlerClass{

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String>  ProductNotFoundExceptionHandler(ProductNotFoundException exception){
        Map<String,String> message= new HashMap<>();
        message.put("Status","NOT_FOUND");
        message.put("Message",exception.getMessage());
        return message;
    }

}
