package fdmc.data.repositories;

import fdmc.data.models.Cat;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public final class CatRepository {

    private final Map<String, Cat> cats;

    public CatRepository() {
        this.cats = new ConcurrentHashMap<>();
    }

    public Cat getByName(final String catName) {
        if (catName == null) {
            return null;
        }

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
