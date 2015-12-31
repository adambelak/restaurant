package com.epam.training.dp.hw.datasource;


import com.epam.training.dp.hw.product.Product;
import com.epam.training.dp.hw.product.ProductDecorator;
import com.epam.training.dp.hw.product.ProductFactory;

public class ProductDataSource extends AbstractDataSource<Product> {

    @Override
    protected void initItems() {
        items.add(ProductFactory.createHotDog());
        items.add(ProductFactory.createChips());
        items.add(ProductDecorator.decorateHotDog().withKetchup().build());
        items.add(ProductDecorator.decorateHotDog().withMustard().build());
        items.add(ProductDecorator.decorateHotDog().withKetchup().withKetchup().build());
        items.add(ProductDecorator.decorateChips().withKetchup().build());
        items.add(ProductDecorator.decorateChips().withMustard().build());
    }
}
