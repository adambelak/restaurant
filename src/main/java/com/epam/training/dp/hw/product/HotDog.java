package com.epam.training.dp.hw.product;

import com.epam.training.dp.hw.guest.Guest;

import java.math.BigDecimal;

public class HotDog implements Product {

    @Override
    public void consume(Guest guest) {
        if (guest != null) {
            BigDecimal happiness = guest.getHappiness().add(BigDecimal.valueOf(2));
            guest.setHappiness(happiness);
        }
    }

    @Override
    public String toString() {
        return "HotDog{}";
    }
}
