package com.epam.training.dp.hw;

import com.epam.training.dp.hw.datasource.DataSource;
import com.epam.training.dp.hw.datasource.GuestDataSource;
import com.epam.training.dp.hw.datasource.ProductDataSource;
import com.epam.training.dp.hw.guest.DefaultGuest;
import com.epam.training.dp.hw.guest.Guest;
import com.epam.training.dp.hw.product.Product;
import com.epam.training.dp.hw.product.ProductDecorator;
import com.epam.training.dp.hw.restaurant.DefaultRestaurant;
import com.epam.training.dp.hw.restaurant.Restaurant;
import com.epam.training.dp.hw.service.DefaultOrderService;
import com.epam.training.dp.hw.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    private Application() {
    }

    public static void main(String[] args) throws InterruptedException {
        LOGGER.trace("Restaurant Application Start");
        OrderService orderService = new DefaultOrderService();
        Thread orderServiceThread = new Thread(orderService, "order-service-thread");
        orderServiceThread.start();

        Restaurant firstRestaurant = new DefaultRestaurant(orderService);
        Thread firstRestaurantThread = new Thread(firstRestaurant, "first-restaurant-thread");
        firstRestaurantThread.start();

        Restaurant secondRestaurant = new DefaultRestaurant(orderService);
        Thread secondRestaurantThread = new Thread(secondRestaurant, "second-restaurant-thread");
        secondRestaurantThread.start();

        orderService.addRestaurant(firstRestaurant);
        orderService.addRestaurant(secondRestaurant);

        DataSource<Product> productDataSource = new ProductDataSource();
        DataSource<Guest> guestDataSource = new GuestDataSource();


        for (int i = 0; i < 5; i++) {
            orderService.order(guestDataSource.readRandomItem(), productDataSource.readRandomItem());
        }

        orderService.stop();
        firstRestaurant.close();
        secondRestaurant.close();

        firstRestaurantThread.join();
        secondRestaurantThread.join();
        orderServiceThread.join();


    }

}
