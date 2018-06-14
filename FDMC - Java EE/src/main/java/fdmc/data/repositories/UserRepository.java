package fdmc.data.repositories;

import fdmc.data.models.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class UserRepository {

    private final Map<String, User> users;

    public UserRepository() {
        this.users = new ConcurrentHashMap<>();
    }

    public User getByUsername(final String username) {
        if (username == null) {
            return null;
        }

        return this.users.get(username);
    }

    public boolean addUser(final User user) {
        if (user == null) {
            return false;
        }

        return this.users.putIfAbsent(user.getUsername(), user) == null;
    }

    public boolean isValidCredentials(final String username, final String password) {
        final User user = this.getByUsername(username);
        return user != null && user.isPasswordValid(password);
    }

    public boolean isAdmin(final String username) {
        final User user = this.getByUsername(username);
        return user != null && user.isAdmin();
    }
}
