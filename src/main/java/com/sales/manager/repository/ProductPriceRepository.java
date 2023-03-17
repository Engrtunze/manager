package com.sales.manager.repository;

import com.sales.manager.model.ProductPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductPriceRepository extends JpaRepository<ProductPrice, UUID> {
    Optional<ProductPrice> findByCurrentPriceAndProductName(BigDecimal price, String name);
    List<ProductPrice> findAllByProduct_Id(UUID id);

}
