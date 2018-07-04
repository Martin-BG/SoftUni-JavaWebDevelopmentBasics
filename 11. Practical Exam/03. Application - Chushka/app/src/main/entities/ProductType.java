package entities;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum ProductType {
    FOOD("Food"),
    DOMESTIC("Domestic"),
    HEALTH("Health"),
    COSMETIC("Cosmetic"),
    OTHER("Other");

    private final String name;

    ProductType(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public static List<String> getAsList() {
        return Stream
                .of(ProductType.values())
                .map(ProductType::toString)
                .collect(Collectors.toList());
    }
}
