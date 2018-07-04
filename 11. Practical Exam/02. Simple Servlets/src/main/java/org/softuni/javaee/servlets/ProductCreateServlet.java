package org.softuni.javaee.servlets;

import org.softuni.javaee.models.Product;
import org.softuni.javaee.models.ProductType;
import org.softuni.javaee.repositories.ProductRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

@WebServlet("/products/create")
public class ProductCreateServlet extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/create.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        this.createProduct(req);

        resp.sendRedirect("/products/all");
    }

    private void createProduct(final HttpServletRequest req) {
        final Product product = new Product(
                req.getParameter("name"),
                req.getParameter("description"),
                ProductType.valueOf(req.getParameter("type").toUpperCase(Locale.ENGLISH)));

        ((ProductRepository) this.getServletContext().getAttribute("products")).add(product);
    }
}
