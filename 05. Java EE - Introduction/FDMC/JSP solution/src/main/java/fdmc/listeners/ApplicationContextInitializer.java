package fdmc.listeners;

import fdmc.data.Cat;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.HashMap;

public class ApplicationContextInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(final ServletContextEvent sce) {
        sce.getServletContext().setAttribute("cats", new HashMap<String, Cat>());
    }
}
