package fdmc.data.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class Order {

    private final User client;
    private final Cat cat;
    private final LocalDateTime madeOn;

    public Order(final User client, final Cat cat) {
        this.client = client;
        this.cat = cat;
        this.madeOn = LocalDateTime.now();
    }

    public User getClient() {
        return this.client;
    }

    public Cat getCat() {
        return this.cat;
    }

    public LocalDateTime getMadeOn() {
        return LocalDateTime.of(this.madeOn.toLocalDate(), this.madeOn.toLocalTime());
    }

    @Override
    public String toString() {
        return "Order{" +
                "client=" + this.client.getUsername() +
                ", cat=" + this.cat.getName() +
                ", madeOn=" + this.madeOn.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) +
                '}';
    }
}
