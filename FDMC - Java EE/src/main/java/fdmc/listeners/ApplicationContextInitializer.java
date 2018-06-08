package fdmc.listeners;

import fdmc.data.repositories.CatRepository;
import fdmc.data.repositories.OrderRepository;
import fdmc.data.repositories.UserRepository;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public final class ApplicationContextInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(final ServletContextEvent sce) {
        sce.getServletContext().setAttribute("cats", new CatRepository());
        sce.getServletContext().setAttribute("users", new UserRepository());
        sce.getServletContext().setAttribute("orders", new OrderRepository());
    }
}
