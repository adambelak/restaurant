package com.epam.training.dp.hw.product;

import com.epam.training.dp.hw.guest.Guest;

import java.math.BigDecimal;

public class ProductWithKethup implements Product {

    private final Product product;

    public ProductWithKethup(Product product) {
        this.product = product;
    }

    @Override
    public void consume(Guest guest) {
        if (guest != null) {
            BigDecimal happiness = guest.getHappiness();
            product.consume(guest);
            BigDecimal modifiedHappiness = guest.getHappiness();
            BigDecimal newHappiness = happiness.add(modifiedHappiness.subtract(happiness).multiply(BigDecimal.valueOf(2)));
            guest.setHappiness(newHappiness);
        }
    }

    @Override
    public String toString() {
        return "ProductWithKethup{" +
                "product=" + product +
                '}';
    }
}
