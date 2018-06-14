package fdmc.servlets.orders;

import fdmc.data.models.Cat;
import fdmc.data.models.Order;
import fdmc.data.models.User;
import fdmc.data.repositories.CatRepository;
import fdmc.data.repositories.OrderRepository;
import fdmc.util.LoggedUser;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/orders/order")
public final class OrderCatServlet extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        final Order order = createOrder(req);

        if (order == null) {
            resp.sendRedirect("/");
            return;
        }

        switch (order.getClient().getRole()) {
        case ADMIN:
            resp.sendRedirect("/orders/all");
            break;
        case USER:
            resp.sendRedirect("/cats/all");
            break;
        default:
            resp.sendRedirect("/");
        }
    }

    private Order createOrder(final HttpServletRequest req) {
        final User client = LoggedUser.get(req);

        final Cat cat = ((CatRepository) this.getServletContext().getAttribute("cats"))
                .getByName(req.getParameter("catName"));

        if (client != null && cat != null) {
            final Order order = new Order(client, cat);
            ((OrderRepository) this.getServletContext().getAttribute("orders")).addOrder(order);
            return order;
        }

        return null;
    }
}
