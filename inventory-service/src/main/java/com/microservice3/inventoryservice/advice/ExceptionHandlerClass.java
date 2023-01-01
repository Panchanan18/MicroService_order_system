package com.microservice3.inventoryservice.advice;

import com.microservice3.inventoryservice.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlerClass {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String,String> resourceNotFoundExceptionHandler(ResourceNotFoundException e){
        Map<String,String> errorMsg= new HashMap<>();
        errorMsg.put("status","NOT_FOUND");
        errorMsg.put("message",e.getMessage());
        return errorMsg;
    }

}
