package javache.database.repositories;

import javache.database.entities.User;
import javache.database.util.CsvFileManager;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public final class UserRepository {

    private final Map<String, User> users;
    private final CsvFileManager fileManager;

    public UserRepository(final CsvFileManager fileManager) {
        this.fileManager = fileManager;
        this.users = this.fileManager.readAll();
    }

    public boolean add(final User user) {
        if (this.users.containsKey(user.getName())) {
            return false;
        }

        final boolean result = this.users.put(user.getName(), user) == null;

        if (result) {
            this.flushData();
        }
        return result;
    }

    private boolean remove(final String name) {
        final boolean result = this.users.remove(name) != null;
        if (result) {
            this.flushData();
        }
        return result;
    }

    private boolean update(final User user) {
        if (!this.users.containsKey(user.getName())) {
            return false;
        }

        this.users.put(user.getName(), user);

        this.flushData();

        return true;
    }

    public User find(final String name) {
        return this.users.get(name);
    }

    public Collection<User> getAll() {
        return Collections.unmodifiableCollection(this.users.values());
    }

    private void flushData() {
        this.fileManager.saveAll(this.getAll());
    }
}
