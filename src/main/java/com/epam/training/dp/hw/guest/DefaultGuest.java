package com.epam.training.dp.hw.guest;

import com.epam.training.dp.hw.product.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.UUID;

public class DefaultGuest implements Guest {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultGuest.class);

    private String name;
    private BigDecimal happiness;

    public DefaultGuest(BigDecimal happiness) {
        this.happiness = happiness;
        this.name = UUID.randomUUID().toString();
    }

    public static DefaultGuest createNormalGuest() {
        return new DefaultGuest(BigDecimal.valueOf(20));
    }

    public static DefaultGuest createSadGuest() {
        return new DefaultGuest(BigDecimal.ONE);
    }

    @Override
    public BigDecimal getHappiness() {
        return happiness;
    }

    @Override
    public void setHappiness(BigDecimal happiness) {
        LOGGER.trace("set {} happiness from {} to {}", name, this.happiness, happiness);
        this.happiness = happiness;
    }

    @Override
    public void receive(Product product) {
        LOGGER.trace("{} consume {}", name, product);
        product.consume(this);
    }

    @Override
    public String toString() {
        return "DefaultGuest{" +
                "name='" + name + '\'' +
                ", happiness=" + happiness +
                '}';
    }
}
