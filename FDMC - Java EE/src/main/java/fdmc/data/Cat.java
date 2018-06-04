package fdmc.data;

public class Cat {

    private final String name;
    private final String breed;
    private final String color;
    private final int numberOfLegs;

    public Cat(final String name,
               final String breed,
               final String color,
               final int numberOfLegs) {
        this.name = name;
        this.breed = breed;
        this.color = color;
        this.numberOfLegs = numberOfLegs;
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
