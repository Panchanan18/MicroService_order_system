package com.microservice.microservice3.productservice.controller;

import com.microservice.microservice3.productservice.exception.ProductNotFoundException;
import com.microservice.microservice3.productservice.model.Product;
import com.microservice.microservice3.productservice.model.ProductPage;
import com.microservice.microservice3.productservice.model.ProductResponse;
import com.microservice.microservice3.productservice.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/create")
    @ApiOperation(value = "Create and Save Product")
    public ResponseEntity<ProductResponse>saveProduct(@RequestBody Product product){
        return new ResponseEntity<>(productService.saveProduct(product), HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    @ApiOperation(value="Get All Products")
    public ResponseEntity<ProductPage> getAllProducts(@RequestParam(value="pageSize", defaultValue ="3", required = false) int pageSize,
                                                      @RequestParam(value = "pageNumber", defaultValue = "1", required = false) int pageNumber,
                                                      @RequestParam(value="sortBy", defaultValue = "id",required = false) String sortBy,
                                                      @RequestParam(value="sortDir", defaultValue = "asc"  , required = false) String sortDir){
        return new ResponseEntity<>(productService.getAllProducts(pageSize,pageNumber,sortBy,sortDir), HttpStatus.OK);
    }

    @GetMapping("/getById/{id}")
    @ApiOperation(value="Get Products By Id")
    public ResponseEntity<Product> getById(@PathVariable String id) throws ProductNotFoundException {
        return new ResponseEntity<>(productService.getById(id),HttpStatus.OK);
    }

    @PutMapping("/update")
    @ApiOperation(value="Update Product")
    public ResponseEntity<ProductResponse>  updateProduct(@RequestBody Product product ) throws ProductNotFoundException {

        return new ResponseEntity<>(productService.updateProduct(product),HttpStatus.OK);
    }

    @DeleteMapping("/deleteById/{id}")
    @ApiOperation(value="Delete Product")
    public ResponseEntity<ProductResponse> deleteProduct(@PathVariable String id ) throws ProductNotFoundException {
        return  new ResponseEntity<>(productService.deleteProduct(id),HttpStatus.OK);
    }


}
