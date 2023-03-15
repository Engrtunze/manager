package com.sales.manager.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Builder
@Table(name = "orders")
public class Order extends AbstractAuditable {
    @Column(name = "quantity", nullable = false)
    private int quantity;
    @Column(name = "customer_name", nullable = false)
    private String customerName;
    @Column(name = "customer_phone_number", nullable = false)
    private String customerPhoneNumber;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;
}
