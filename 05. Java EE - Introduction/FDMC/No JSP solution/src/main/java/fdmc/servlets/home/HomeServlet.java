package fdmc.servlets.home;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/")
public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {

        final String html = "" +
                "<h1>Welcome to Fluffy Duffy Munchkin Cats!<h1>" +
                "<h3>Navigate through the application using the links below!</h3>" +
                "<hr/>" +
                "<a href=\"/cats/create\">Create Cat</a>" +
                "<br/>" +
                "<a href=\"/cats/all\">All Cats</a>";

        resp.getWriter().write(html);
    }
}
