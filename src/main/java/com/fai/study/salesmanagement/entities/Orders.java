package com.fai.study.salesmanagement.entities;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    Customers customer;

    @Column(name = "order_date")
    @Temporal(TemporalType.TIMESTAMP)
    Date orderDate;

    @Column(name = "total_price")
    double totalPrice;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    List<OrderDetails> orderDetails;
}