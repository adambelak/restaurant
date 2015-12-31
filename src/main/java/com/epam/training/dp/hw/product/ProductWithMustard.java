package com.epam.training.dp.hw.product;

import com.epam.training.dp.hw.guest.Guest;

import java.math.BigDecimal;

public class ProductWithMustard implements Product {

    private final Product product;

    public ProductWithMustard(Product product) {
        this.product = product;
    }

    @Override
    public void consume(Guest guest) {
        if (guest != null) {
            BigDecimal happiness = guest.getHappiness();
            guest.setHappiness(happiness.add(BigDecimal.ONE));
        }
    }

    @Override
    public String toString() {
        return "ProductWithMustard{" +
                "product=" + product +
                '}';
    }
}
