package com.microservice.microservice3.productservice.service;

import com.microservice.microservice3.productservice.exception.ProductNotFoundException;
import com.microservice.microservice3.productservice.model.Product;
import com.microservice.microservice3.productservice.model.ProductResponse;
import com.microservice.microservice3.productservice.repos.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public ProductResponse saveProduct(Product productRequest) {
        boolean exists = productRepository.existsById(productRequest.getId());
        if (exists){
            return new ProductResponse(productRequest.getId(),productRequest.getName(),productRequest.getDesc(),productRequest.getPrice(),"Already Exists");
        }
        Product product = productRepository.save(productRequest);
        return new ProductResponse(product.getId(),product.getName(),product.getDesc(),product.getPrice(),"Created");
    }

    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products;
    }

    public Product getById(String id) throws ProductNotFoundException {
         return productRepository.findById(id).orElseThrow(()->new ProductNotFoundException("Product with id "+id+" is not found"));
    }

    public ProductResponse updateProduct(Product product) throws ProductNotFoundException {
        Product product1 = productRepository.findById(product.getId()).orElseThrow(() -> new ProductNotFoundException("Product with id " + product.getId() + " is not found"));
        product1.setName(product.getName());
        product1.setDesc(product.getDesc());
        product1.setPrice(product.getPrice());
        Product savedProduct = productRepository.save(product1);
        return new ProductResponse(product.getId(),product.getName(),product.getDesc(),product.getPrice(),"Updated");

    }

    public ProductResponse deleteProduct(String id) throws ProductNotFoundException {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " is not found"));
        productRepository.deleteById(id);
        return new ProductResponse(product.getId(),product.getName(),product.getDesc(),product.getPrice(),"Deleted");
    }
}
