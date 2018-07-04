package org.softuni.javaee.repositories;

import org.softuni.javaee.models.Product;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ProductRepository {

    private final Map<String, Product> products;

    public ProductRepository() {
        this.products = new ConcurrentHashMap<>();
    }

    public Product getByName(final String productName) {
        if (productName == null) {
            return null;
        }

        return this.products.get(productName);
    }

    public Collection<Product> getAll() {
        return Collections.unmodifiableCollection(this.products.values());
    }

    public void add(final Product product) {
        if (product == null) {
            return;
        }

        this.products.putIfAbsent(product.getName(), product);
    }
}
