package fdmc.data.repositories;

import fdmc.data.models.User;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class UserRepository {
    private Map<String, User> users;

    public UserRepository() {
        this.users = new HashMap<>();
    }

    public User getByUsername(final String username) {
        return this.users.get(username);
    }

    public Collection<User> getAllUsers() {
        return Collections.unmodifiableCollection(this.users.values());
    }

    public boolean addUser(final User user) {
        if (user == null) {
            return false;
        }

        return this.users.putIfAbsent(user.getUsername(), user) == null;
    }

    public boolean userExists(final String username) {
        return this.getByUsername(username) != null;
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
