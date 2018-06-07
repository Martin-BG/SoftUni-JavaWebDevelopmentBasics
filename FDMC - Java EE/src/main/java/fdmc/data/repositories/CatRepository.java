package fdmc.data.repositories;

import fdmc.data.models.Cat;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CatRepository {
    private Map<String, Cat> cats;

    public CatRepository() {
        this.cats = new HashMap<>();
    }

    public Cat getByName(final String catName) {
        return this.cats.get(catName);
    }

    public Collection<Cat> getAllCats() {
        return Collections.unmodifiableCollection(this.cats.values());
    }

    public boolean addCat(final Cat cat) {
        if (cat == null) {
            return false;
        }

        return this.cats.putIfAbsent(cat.getName(), cat) == null;
    }
}
