package com.epam.training.dp.hw.product;

import com.epam.training.dp.hw.guest.Guest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import java.math.BigDecimal;

public class ChipsTest {

    public static final int ONE_HUNDRED = 100;
    public static final int ONE_HUNDRED_AND_FIVE = 105;
    public static final String MULTIPLIER = "1.05";

    private Product testSubject;

    @Before
    public void setUp() throws Exception {
        testSubject = new Chips();
    }

    @Test
    public void testConsumeShouldDoNothingWhenGuestIsNull() throws Exception {
        // GIVEN
        Guest guest = null;
        // WHEN
        testSubject.consume(guest);
        // THEN
        Assert.assertNull("Guest is null, nothing happened.", guest);
    }

    @Test
    public void testConsumeShouldBeGoodWhenGuestIsValid() throws Exception {
        // GIVEN
        Guest guest = Mockito.mock(Guest.class);
        // WHEN
        Mockito.when(guest.getHappiness()).thenReturn(BigDecimal.valueOf(ONE_HUNDRED));
        testSubject.consume(guest);
        Mockito.when(guest.getHappiness()).thenReturn(BigDecimal.valueOf(ONE_HUNDRED_AND_FIVE));
        // THEN
        Mockito.verify(guest).getHappiness();
        Mockito.verify(guest).setHappiness(Matchers.eq(BigDecimal.valueOf(ONE_HUNDRED).multiply(new BigDecimal(MULTIPLIER))));
        Mockito.verify(guest).getHappiness();
    }
}
