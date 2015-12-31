package com.epam.training.dp.hw.restaurant;

import com.epam.training.dp.hw.product.Product;

public interface Restaurant extends Runnable {

    void order(Product product);

    void close();

}
