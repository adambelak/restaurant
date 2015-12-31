package com.epam.training.dp.hw.service;

import com.epam.training.dp.hw.guest.Guest;
import com.epam.training.dp.hw.product.Product;
import com.epam.training.dp.hw.restaurant.Restaurant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class DefaultOrderService implements OrderService, Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultOrderService.class);

    private final Set<Restaurant> restaurants;
    private final Map<Guest, Product> orders;
    private final Map<Guest, Product> processing;
    private final Object lock;
    private boolean running;

    public DefaultOrderService() {
        restaurants = new CopyOnWriteArraySet<>();
        orders = new ConcurrentHashMap<>();
        processing = new ConcurrentHashMap<>();
        running = true;
        lock = new Object();
    }

    @Override
    public void addRestaurant(Restaurant restaurant) {
        restaurants.add(restaurant);
        LOGGER.trace("Restaurant {} has been added", restaurant);
        notifyProcessor();
    }

    private void notifyProcessor() {
        synchronized (lock) {
            lock.notifyAll();
        }
    }

    @Override
    public void order(Guest guest, Product product) {
        orders.put(guest, product);
        LOGGER.trace("Please create a {} for {}", product, guest);
        notifyProcessor();
    }

    @Override
    public void deliver(Product product) {
        Optional<Map.Entry<Guest, Product>> order = processing.entrySet().stream().filter(e -> e.getValue().equals(product)).findFirst();
        if (order.isPresent()) {
            order.get().getKey().receive(product);
            processing.remove(order.get().getKey());
            LOGGER.trace("{} has been delivered", product);
        } else {
            LOGGER.debug("{} not found!", product);
        }
    }

    @Override
    public void stop() {
        running = false;
        notifyProcessor();
    }

    @Override
    public void run() {
        synchronized (lock) {
            while (running || !orders.isEmpty()) {
                waitForProductAndRestaurant();
                process();
            }
        }
    }

    private void waitForProductAndRestaurant() {
        try {
            while (orders.isEmpty() || restaurants.isEmpty()) {
                lock.wait();
            }
        } catch (InterruptedException e) {
            LOGGER.info("waitForProductAndRestaurant() has been interrupted.");
        }
    }

    private Optional<Restaurant> chooseRestaurant() {
        List<Restaurant> copyOfRestaurants = new ArrayList<>(restaurants);
        Optional<Restaurant> result = Optional.empty();
        Collections.shuffle(copyOfRestaurants);
        if (!copyOfRestaurants.isEmpty()) {
            result = Optional.of(copyOfRestaurants.get(0));
        }
        return result;
    }

    private void process() {
        Optional<Restaurant> restaurant = chooseRestaurant();
        if (restaurant.isPresent()) {
            processOrder(restaurant.get());
        } else {
            LOGGER.error("Restaurant not found.");
        }
    }

    private void processOrder(Restaurant restaurant) {
        Optional<Map.Entry<Guest, Product>> order = orders.entrySet().stream().findFirst();
        if (order.isPresent()) {
            processing.put(order.get().getKey(), order.get().getValue());
            orders.remove(order.get().getKey());
            restaurant.order(order.get().getValue());
        } else {
            LOGGER.error("Orders is empty.");
        }
    }

}
