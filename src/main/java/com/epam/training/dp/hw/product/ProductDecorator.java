package com.epam.training.dp.hw.product;

public class ProductDecorator {

    private Product product;

    private ProductDecorator(Product product) {
        this.product = product;
    }

    public static ProductDecorator decorate(Product product) {
        return new ProductDecorator(product);
    }

    public static ProductDecorator decorateHotDog() {
        return new ProductDecorator(ProductFactory.createHotDog());
    }

    public static ProductDecorator decorateChips() {
        return new ProductDecorator(ProductFactory.createChips());
    }

    public ProductDecorator withKetchup() {
        product = new ProductWithKethup(product);
        return this;
    }

    public ProductDecorator withMustard() {
        product = new ProductWithMustard(product);
        return this;
    }

    public Product build() {
        return product;
    }

}
