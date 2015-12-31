package com.epam.training.dp.hw.datasource;

import com.epam.training.dp.hw.guest.DefaultGuest;
import com.epam.training.dp.hw.guest.Guest;

public class GuestDataSource extends AbstractDataSource<Guest> {

    @Override
    protected void initItems() {
        items.add(DefaultGuest.createNormalGuest());
        items.add(DefaultGuest.createNormalGuest());
        items.add(DefaultGuest.createSadGuest());
    }
}
