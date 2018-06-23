package org.softuni.javaservlet.servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@WebServlet("/tubes/details")
public class TubesDetailsServlet extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
/*      // Method 1:
        final String html = "" +
                "<h1>Successfully created Tube - " + this.decodeParameter(req.getParameter("title")) + "</h1>" +
                "<hr/>" +
                "<h3>Description:</h3>" + "<p>" + this.decodeParameter(req.getParameter("description")) + "</p>" +
                "<hr/>" +
                "<h3>Video Link</h3>" + "<p>" + this.decodeParameter(req.getParameter("videoLink")) + "</p>";*/

        // Method 3:
        final String html = "" +
                "<h1>Successfully created Tube - " + this.decodeParameter(this.getServletContext().getAttribute("title").toString()) + "</h1>" +
                "<hr/>" +
                "<h3>Description:</h3>" + "<p>" + this.decodeParameter(this.getServletContext().getAttribute("description").toString()) + "</p>" +
                "<hr/>" +
                "<h3>Video Link</h3>" + "<p>" + this.decodeParameter(this.getServletContext().getAttribute("videoLink").toString()) + "</p>";

        resp.getWriter().write(html);
    }

    private String decodeParameter(String encoded) {
        try {
            return URLDecoder.decode(encoded, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }
}
