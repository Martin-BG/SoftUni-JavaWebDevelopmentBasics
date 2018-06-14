package fdmc.servlets.orders;

import fdmc.data.models.User;
import fdmc.data.repositories.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/orders/all")
public final class OrdersAllServlet extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        final User user = ((UserRepository) this.getServletContext().getAttribute("users"))
                .getByUsername((String) req.getSession().getAttribute("username"));

        if (user == null || !user.isAdmin()) {
            resp.sendRedirect("/");
            return;
        }

        req.getRequestDispatcher("/WEB-INF/jsp/orders/all.jsp").forward(req, resp);
    }
}
