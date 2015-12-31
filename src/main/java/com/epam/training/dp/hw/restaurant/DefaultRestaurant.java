package com.epam.training.dp.hw.restaurant;

import com.epam.training.dp.hw.product.Product;
import com.epam.training.dp.hw.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class DefaultRestaurant implements Restaurant {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultRestaurant.class);
    private static final Product POISON_KILL_PRODUCT = guest -> {};

    private final Object lock;
    private String name;
    private OrderService orderService;
    private List<Product> products;
    private boolean open;

    public DefaultRestaurant(OrderService orderService) {
        this.open = true;
        this.name = UUID.randomUUID().toString();
        this.orderService = orderService;
        this.products = new CopyOnWriteArrayList<>();
        this.lock = new Object();
    }

    @Override
    public void order(Product product) {
        if (open) {
            products.add(product);
            notifyProcessor();
        }
    }

    @Override
    public void close() {
        if (open) {
            open = false;
            products.add(POISON_KILL_PRODUCT);
            notifyProcessor();
        }
    }

    @Override
    public void run() {
        synchronized (lock) {
            while (open || !products.isEmpty()) {
                waitForProduct();
                Product product = prepareProduct();
                if (!POISON_KILL_PRODUCT.equals(product)) {
                    deliver(product);
                }
            }
        }
    }

    private void waitForProduct() {
        try {
            while (open && products.isEmpty()) {
                lock.wait();
            }
        } catch (InterruptedException e) {
            LOGGER.info("waitForProduct() has been interrupted.");
        }
    }

    private Product prepareProduct() {
        Product product = products.remove(0);
        LOGGER.trace("prepare {}", product);
        return product;
    }

    private void deliver(Product product) {
        LOGGER.trace("{} is ready.", product);
        orderService.deliver(product);
    }

    private void notifyProcessor() {
        new Thread(() -> {
            synchronized (lock) {
                lock.notifyAll();
            }
        }).start();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DefaultRestaurant that = (DefaultRestaurant) o;

        return !(name != null ? !name.equals(that.name) : that.name != null);

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "DefaultRestaurant{" +
                "name='" + name + '\'' +
                '}';
    }
}
