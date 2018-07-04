package org.softuni.javaee.listeners;

import org.softuni.javaee.model.Product;
import org.softuni.javaee.model.ProductType;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.HashMap;
import java.util.LinkedHashMap;

public final class ApplicationContextInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(final ServletContextEvent sce) {
        sce.getServletContext().setAttribute("products", new LinkedHashMap<String, Product>() {{
            put("Chushkopek", new Product("Chushkopek", "A universal tool for ...", ProductType.DOMESTIC ));
            put("Injektoplqktor", new Product("Injektoplqktor", "Dunno what this is ...", ProductType.COSMETIC ));
            put("Plumbus", new Product("Plumbus", "A domestic tool for everything", ProductType.FOOD ));
        }});
    }
}
