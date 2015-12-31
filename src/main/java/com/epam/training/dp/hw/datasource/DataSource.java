package com.epam.training.dp.hw.datasource;

import java.util.List;
import java.util.Optional;

public interface DataSource<T> {
    List<T> readAll();

    T readRandomItem();

    Optional<T> readItem(int index);
}
