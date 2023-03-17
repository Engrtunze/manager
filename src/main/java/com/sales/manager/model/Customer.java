package com.sales.manager.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "customer")
public class Customer extends AbstractAuditable{
    @Column(name = "customer_name", nullable = false)
    private String customerName;
    @Column(name = "customer_phone_number", nullable = false)
    private String customerPhoneNumber;
}
