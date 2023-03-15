package com.sales.manager.repository;

import com.sales.manager.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    Optional<Product> findByNameAndPrice(String name, BigDecimal price);
    List<Product> findAllByProductQuantityGreaterThan(int quantity);
}
