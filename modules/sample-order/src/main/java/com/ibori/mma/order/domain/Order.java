package com.ibori.mma.order.domain;

import com.ibori.framework.jpa.entity.BaseEntity;
import com.ibori.framework.jpa.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "orders")
@NoArgsConstructor
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;
    private int quantity;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public Order(String productName, int quantity) {
        this.productName = productName;
        this.quantity = quantity;
        this.status = OrderStatus.CREATED;
    }
}