package fdmc.data.models;

import java.util.Objects;

public class Cat {

    private final String name;
    private final String breed;
    private final String color;
    private final int numberOfLegs;
    private final User creator;

    public Cat(final String name,
               final String breed,
               final String color,
               final int numberOfLegs,
               final User creator) {
        this.name = Objects.requireNonNull(name);
        this.breed = Objects.requireNonNull(breed);
        this.color = Objects.requireNonNull(color);
        this.numberOfLegs = numberOfLegs;
        this.creator = Objects.requireNonNull(creator);
    }

    public String getName() {
        return this.name;
    }

    public String getBreed() {
        return this.breed;
    }

    public String getColor() {
        return this.color;
    }

    public int getNumberOfLegs() {
        return this.numberOfLegs;
    }

    public User getCreator() {
        return this.creator;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "name='" + name + '\'' +
                ", breed='" + breed + '\'' +
                ", color='" + color + '\'' +
                ", numberOfLegs=" + numberOfLegs +
                '}';
    }
}
