package com.springapps.shop.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Order {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column
    private Double totalPrice;

    @OneToMany (mappedBy = "order",cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonManagedReference("order-orderItem")
    private List<OrderItem> orderItems;

    @OneToMany (mappedBy = "order",cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonManagedReference("order-cartItem")
    private List<CartItem> cartItems;

    @ManyToOne
    @JsonBackReference("user-order")
    @JoinColumn(name = "user_id")
    private User user;

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
