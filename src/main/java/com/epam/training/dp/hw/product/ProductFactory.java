package com.epam.training.dp.hw.product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ProductFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductFactory.class);

    private static final int CACHE_INIT_SIZE = 2;
    private static final Map<Class, Product> CACHE = new ConcurrentHashMap<>(CACHE_INIT_SIZE);

    public static Product createHotDog() {
        return getSimpleProduct(HotDog.class);
    }

    public static Product createChips() {
        return getSimpleProduct(Chips.class);
    }

    private static Product getSimpleProduct(Class productType) {
        if (!CACHE.containsKey(productType)) {
            try {
                CACHE.put(productType, (Product) productType.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                LOGGER.debug("Can't create Product: " + e.getMessage());
            }
        }
        return CACHE.get(productType);
    }

}
