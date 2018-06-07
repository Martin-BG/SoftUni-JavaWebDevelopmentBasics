package fdmc.data.models;

import java.util.Objects;

public class Cat {

    private final String name;
    private final String breed;
    private final String color;
    private final int numberOfLegs;
    private final User creator;
    private int views;

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
        this.views = 0;
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

    public int getViews() {
        return this.views;
    }

    synchronized public void increaseViews() {
        this.views++;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "name='" + this.name + '\'' +
                ", breed='" + this.breed + '\'' +
                ", color='" + this.color + '\'' +
                ", numberOfLegs=" + this.numberOfLegs +
                ", creator=" + this.creator.getUsername() +
                ", views=" + this.views +
                '}';
    }
}
