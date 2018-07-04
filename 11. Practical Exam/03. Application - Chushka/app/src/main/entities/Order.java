package entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {
    private String id;

    private Product product;

    private User client;

    private LocalDateTime orderedOn;

    public Order() {
    }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @ManyToOne(targetEntity = Product.class)
    @JoinColumn(name = "product_id", nullable = false)
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "client_id", nullable = false)
    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    @Column(nullable = false)
    public LocalDateTime getOrderedOn() {
        return orderedOn;
    }

    public void setOrderedOn(LocalDateTime orderedOn) {
        this.orderedOn = orderedOn;
    }

    /*
             <tr class="row">
                <th class="col-md-1">1</th>
                <td class="col-md-4">c57019d0-a9bc-40f3-b73b-89fcf6a101fd</td>
                <td class="col-md-2">Pesho</td>
                <td class="col-md-2">Chushkopek</td>
                <td class="col-md-2">15:30 21/12/2012</td>
            </tr>
     */
    public String extractOrdersView(int num) {
        return (new StringBuilder("<tr class=\"row\">")
                .append("<th class=\"col-md-1\">" + num + "</th>")
                .append("<td class=\"col-md-4\">" + this.getId() + "</th>")
                .append("<td class=\"col-md-2\">" + this.getClient().getUsername() + "</th>")
                .append("<td class=\"col-md-2\">" + this.getProduct().getName() + "</th>")
                .append("<td class=\"col-md-2\">" + this.getOrderedOn() + "</th>")
                .append("</tr>")
        ).toString();
    }
}
