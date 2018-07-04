package controllers;

import entities.Order;
import entities.Role;
import org.softuni.broccolina.solet.HttpSoletRequest;
import org.softuni.summermvc.api.Controller;
import org.softuni.summermvc.api.GetMapping;
import org.softuni.summermvc.api.Model;
import repositories.OrderRepository;
import repositories.ProductRepository;
import repositories.UserRepository;

import java.util.List;

@Controller
public class OrdersController extends BaseController {

    private UserRepository userRepository;

    private ProductRepository productRepository;

    private OrderRepository orderRepository;

    public OrdersController() {
        this.userRepository = new UserRepository();
        this.productRepository = new ProductRepository();
        this.orderRepository = new OrderRepository();
    }

    @GetMapping(route = "/orders/all")
    public String index(HttpSoletRequest request, Model model) {
        if (!super.isLoggedIn(request) || super.getRole(request) != Role.ADMIN) {
            return super.redirect("/");
        }

        final List<Order> allOrders = orderRepository.findAll();
        final StringBuilder renderedOrders = new StringBuilder();
        for (int i = 0; i < allOrders.size(); i++) {
            renderedOrders.append(allOrders.get(i).extractOrdersView(i + 1));
        }
        model.addAttribute("orders", renderedOrders.toString());

        return super.view("orders-all");
    }
}
