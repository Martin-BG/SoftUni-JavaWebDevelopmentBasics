package org.softuni.javaee.listeners;

import org.softuni.javaee.models.Product;
import org.softuni.javaee.models.ProductType;
import org.softuni.javaee.repositories.ProductRepository;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public final class ApplicationContextInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(final ServletContextEvent sce) {
        sce.getServletContext().setAttribute("products", new ProductRepository() {{
            add(new Product("Chushkopek", "A universal tool for ...", ProductType.DOMESTIC));
            add(new Product("Injektoplqktor", "Dunno what this is ...", ProductType.COSMETIC));
            add(new Product("Plumbus", "A domestic tool for everything", ProductType.FOOD));
        }});
    }
}
