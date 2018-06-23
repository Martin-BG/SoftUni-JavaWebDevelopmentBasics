package org.softuni.javaservlet.servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/tubes/create")
public class TubesCreateServlet extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        final String html = "" +
                "<h1>Create Tube</h1>" +
                "<hr/>" +
                "<form method=\"post\" action=\"/tubes/create\">" +
                "<label for=\"title\">Title: </label>" +
                "<input type=\"text\" id=\"title\" name=\"title\" placeholder=\"Title...\">" +
                "<hr/>" +
                "<label for=\"description\">Description: </label>" +
                "<input type=\"text\" id=\"description\" name=\"description\" placeholder=\"Description...\">" +
                "<hr/>" +
                "<label for=\"videoLink\">Video Link: </label>" +
                "<input type=\"text\" id=\"videoLink\" name=\"videoLink\" placeholder=\"Video Link...\">" +
                "<hr/>" +
                "<button type=\"submit\">Create Tube</button>" +
                "</form>";

        resp.getWriter().write(html);
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
/*      // Method 1: HTML redirect
        final String html = String.format(
                "<head><meta http-equiv=\"refresh\" content=\"0; url=/tubes/details?title=%s&amp;description=%s&amp;videoLink=%s&amp;\" /></head>",
                req.getParameter("title"), req.getParameter("description"), req.getParameter("videoLink"));

        resp.getWriter().write(html);*/

//        this.getServletContext().getRequestDispatcher("/tubes/details").forward(req, resp); //Method 2: this results in POST request to /tubes/details

        // Method 3:
        this.getServletContext().setAttribute("title", req.getParameter("title"));
        this.getServletContext().setAttribute("description", req.getParameter("description"));
        this.getServletContext().setAttribute("videoLink", req.getParameter("videoLink"));
        resp.sendRedirect("/tubes/details");
    }
}
