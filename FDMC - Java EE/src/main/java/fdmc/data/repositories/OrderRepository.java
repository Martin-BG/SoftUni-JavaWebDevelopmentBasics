package fdmc.data.repositories;

import fdmc.data.models.Order;

import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.concurrent.LinkedBlockingDeque;

public final class OrderRepository {

    private final Deque<Order> orders;

    public OrderRepository() {
        this.orders = new LinkedBlockingDeque<>();
    }

    public void addOrder(final Order order) {
        this.orders.addFirst(order);
    }

    public Collection<Order> allOrders() {
        return Collections.unmodifiableCollection(this.orders);
    }
}
