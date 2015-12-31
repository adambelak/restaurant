package com.epam.training.dp.hw.guest;

import com.epam.training.dp.hw.product.Product;

import java.math.BigDecimal;

public interface Guest {

    BigDecimal getHappiness();

    void setHappiness(BigDecimal happiness);

    void receive(Product product);

}
