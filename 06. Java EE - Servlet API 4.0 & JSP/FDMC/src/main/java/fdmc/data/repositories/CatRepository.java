package fdmc.data.repositories;

import fdmc.data.models.Cat;

import java.util.*;
import java.util.stream.Collectors;

public final class CatRepository {

    private final Map<String, Cat> cats;

    public CatRepository() {
        this.cats = new HashMap<>();
    }

    public Cat getByName(final String catName) {
        return this.cats.get(catName);
    }

    public Collection<Cat> getAllCats() {
        return Collections.unmodifiableCollection(
                this.cats.values()
                        .stream()
                        .sorted(Comparator.comparing(Cat::getViews).reversed())
                        .collect(Collectors.toList()));
    }

    public boolean addCat(final Cat cat) {
        if (cat == null) {
            return false;
        }

        return this.cats.putIfAbsent(cat.getName(), cat) == null;
    }
}
