package fdmc.serlets.cat;

import fdmc.data.Cat;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/cats/all")
public class CatsAllServlet extends HttpServlet {

    @Override
    @SuppressWarnings("unchecked")
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        final Map<String, Cat> cats = ((Map<String, Cat>) this.getServletContext().getAttribute("cats"));
        final StringBuilder html = new StringBuilder()
                .append("<h1>All Cats</h1>")
                .append("<hr />");

        if (cats.isEmpty()) {
            html
                    .append("<h3>There are no cats. <a href=\"/cats/create\">Create Some!</a></h1>");
        } else {
            cats.keySet()
                    .forEach(catName -> html
                            .append("<p><a href=\"/cats/profile?catName=")
                            .append(catName).append("\">")
                            .append(catName).append("</a>"));
        }

        html.append("<p><a href=\"/\">Back To Home</a></p>");
        resp.getWriter().write(html.toString());
    }
}
