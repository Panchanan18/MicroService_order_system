package com.microservice3.orderservice.controller;



import com.microservice3.orderservice.dto.OrderRequest;
import com.microservice3.orderservice.model.Order;
import com.microservice3.orderservice.service.OrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/placeOrder")
    @ApiOperation(value = "Place Order")
    public ResponseEntity<Order> placeOrder(@RequestBody OrderRequest orderRequest){
        return new ResponseEntity<>(orderService.placeOrder(orderRequest), HttpStatus.CREATED);
    }


}
