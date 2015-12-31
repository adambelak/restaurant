package com.epam.training.dp.hw.product;

import com.epam.training.dp.hw.guest.Guest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import java.math.BigDecimal;

public class ProductWithKetchupTest {

    private static final int ONE_HUNDRED = 100;
    private static final int ONE_HUNDRED_AND_TWO = 102;
    private static final int ONE_HUNDRED_AND_FOUR = 104;

    private Product testSubject;
    private Product innerProduct;

    @Before
    public void setUp() throws Exception {
        innerProduct = Mockito.mock(Product.class);
        testSubject = new ProductWithKethup(innerProduct);
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


        Mockito.when(guest.getHappiness()).thenReturn(BigDecimal.valueOf(ONE_HUNDRED), BigDecimal.valueOf(ONE_HUNDRED_AND_TWO));
        testSubject.consume(guest);

        // THEN
        Mockito.verify(guest, Mockito.times(2)).getHappiness();
        Mockito.verify(guest).setHappiness(Matchers.eq(BigDecimal.valueOf(ONE_HUNDRED_AND_FOUR)));

    }
}
