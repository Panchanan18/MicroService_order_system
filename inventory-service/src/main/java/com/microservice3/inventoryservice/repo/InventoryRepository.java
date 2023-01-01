package com.microservice3.inventoryservice.repo;

import com.microservice3.inventoryservice.model.Inventory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository  extends MongoRepository<Inventory,String> {



    List<Inventory> findBySkuCodeIn(List<String> skuCodes);



    Optional<Inventory> findBySkuCode(String skuCode);
}
