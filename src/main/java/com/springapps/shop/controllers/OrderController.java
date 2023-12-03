package com.springapps.shop.controllers;

import com.springapps.shop.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

//Pentru plasarea unei comenzi, e nevoie sa:
//Gasesti utilizatorul dupa id
//Iei toate cartItem-urile utilizatorului din baza de date ( te folosesti de metoda cartRepository.findAllByUser)
//Construiesti un nou obiect order, calculezi si pretul total si salvezi acest order in baza de date
//Pentru fiecare cartitem din lista de cartitems ale utilizatorului
//Creezi un nou orderitem
//Il legi de order (orderItem.setOrder(newOrder)
//Salvezi in abza de date acel orderItem
//Stergi toate cartitems-urile utilizatorului

@RestController
public class OrderController {
    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
}
