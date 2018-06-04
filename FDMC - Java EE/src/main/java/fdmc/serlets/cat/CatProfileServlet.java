package fdmc.serlets.cat;

import fdmc.data.Cat;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/cats/profile")
public class CatProfileServlet extends HttpServlet {

    @Override
    @SuppressWarnings("unchecked")
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        final String catName = req.getParameter("catName");
        final Cat cat = ((Map<String, Cat>) this.getServletContext().getAttribute("cats")).get(catName);
        final StringBuilder html = new StringBuilder();

        if (cat == null) {
            html.append("<h1>Cat, with name - ").append(catName).append(" was not found.</h1>");
        } else {
            html
                    .append("<h1>Cat - ").append(cat.getName()).append("</h1>")
                    .append("<hr />")
                    .append("<h3>Breed: ").append(cat.getBreed()).append("</h3>")
                    .append("<h3>Color: ").append(cat.getColor()).append("</h3>")
                    .append("<h3>Number of Legs: ").append(cat.getNumberOfLegs()).append("</h3>");
        }

        html
                .append("<hr />")
                .append("<p><a href=\"/cats/all\">Back</a></p>");

        resp.getWriter().write(html.toString());
    }
}
