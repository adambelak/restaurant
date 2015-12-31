package com.epam.training.dp.hw.datasource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public abstract class AbstractDataSource<T> implements DataSource<T> {

    protected final List<T> items;
    protected final Random random;

    public AbstractDataSource() {
        items = new ArrayList<>();
        random = new Random();
        initItems();
    }

    @Override
    public List<T> readAll() {
        return new ArrayList<>(items);
    }

    @Override
    public T readRandomItem() {
        return items.get(random.nextInt(items.size()));
    }

    @Override
    public Optional<T> readItem(int index) {
        Optional<T> result = Optional.empty();
        if (index < items.size()) {
            result = Optional.of(items.get(0));
        }
        return result;
    }

    protected abstract void initItems();
}
