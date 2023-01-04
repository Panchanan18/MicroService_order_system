package com.microservice.microservice3.productservice.service;

import com.microservice.microservice3.productservice.exception.ProductNotFoundException;
import com.microservice.microservice3.productservice.model.Product;
import com.microservice.microservice3.productservice.model.ProductPage;
import com.microservice.microservice3.productservice.model.ProductResponse;
import com.microservice.microservice3.productservice.repos.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    public ProductPage getAllProducts(int pageSize, int pageNumber, String sortBy,String sortDir) {
        PageRequest pageRequest;
        if(sortDir.equalsIgnoreCase("asc")){
            pageRequest = PageRequest.of(pageNumber-1, pageSize, Sort.by(sortBy).ascending());
        }
        else{
            pageRequest = PageRequest.of(pageNumber-1, pageSize, Sort.by(sortBy).descending());
        }



        Page<Product> all = productRepository.findAll(pageRequest);
        List<Product> productList = all.getContent();
        ProductPage productPage = new ProductPage();
        productPage.setProductList(productList);
        productPage.setPageNumber(all.getNumber()+1);
        productPage.setNoOfElements(all.getSize());
        productPage.setTotalElements(all.getTotalElements());
        productPage.setLastPage(all.isLast());
        productPage.setTotalPages(all.getTotalPages());
        return productPage;
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
