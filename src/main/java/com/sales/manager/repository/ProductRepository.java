package com.sales.manager.repository;

import com.sales.manager.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findAllByProductQuantityGreaterThan(int quantity);

    //@NotNull Optional<Product> findById(@NotNull UUID id);
}
