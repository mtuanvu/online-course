package com.fai.study.salesmanagement.entities;

import jakarta.data.Order;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "order_details")
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    Orders order;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    Products product;

    int quantity;

    @Column(name = "unit_price")
    double unitPrice;
}
