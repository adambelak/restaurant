package com.epam.training.dp.hw.product;

import com.epam.training.dp.hw.guest.Guest;

import java.math.BigDecimal;

public class Chips implements Product {

    @Override
    public void consume(Guest guest) {
        if (guest != null) {
            BigDecimal happiness = guest.getHappiness().multiply(new BigDecimal("1.05"));
            guest.setHappiness(happiness);
        }
    }

    @Override
    public String toString() {
        return "Chips{}";
    }
}
