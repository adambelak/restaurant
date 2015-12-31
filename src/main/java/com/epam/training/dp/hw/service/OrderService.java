package com.epam.training.dp.hw.service;

import com.epam.training.dp.hw.guest.Guest;
import com.epam.training.dp.hw.product.Product;
import com.epam.training.dp.hw.restaurant.Restaurant;

public interface OrderService extends Runnable {

    void addRestaurant(Restaurant restaurant);

    void order(Guest guest, Product product);

    void deliver(Product product);

    void stop();

}
