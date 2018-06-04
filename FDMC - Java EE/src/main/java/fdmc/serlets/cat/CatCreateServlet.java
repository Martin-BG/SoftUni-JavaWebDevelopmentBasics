package fdmc.serlets.cat;

import fdmc.data.Cat;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/cats/create")
public class CatCreateServlet extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        final String html = "" +
                "<h1>Create a Cat!</h1>" +
                "<form method=\"post\">" +
                "<label for=\"name\">Name:</label>" +
                "<input type=\"text\" id=\"name\" name=\"name\">" +
                "<br/>" +
                "<label for=\"breed\">Breed:</label>" +
                "<input type=\"text\" id=\"breed\" name=\"breed\">" +
                "<br/>" +
                "<label for=\"color\">Color:</label>" +
                "<input type=\"text\" id=\"color\" name=\"color\">" +
                "<br/>" +
                "<label for=\"legs\">Number of legs:</label>" +
                "<input type=\"number\" id=\"legs\" name=\"legs\">" +
                "<br/>" +
                "<button type=\"submit\">Create Cat</button>" +
                "<br/>" +
                "<p><a href=\"/\">Back to Home</a></p>" +
                "</form>";

        resp.getWriter().write(html);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        final Cat cat = new Cat(
                req.getParameter("name"),
                req.getParameter("breed"),
                req.getParameter("color"),
                Integer.parseInt(req.getParameter("legs")));

        ((Map<String, Cat>) this.getServletContext().getAttribute("cats")).put(cat.getName(), cat);

        final String html = String.format(
                "<head><meta http-equiv=\"refresh\" content=\"0; url=/cats/profile?catName=%s\" /></head>",
                cat.getName());

        resp.getWriter().write(html);
    }
}
