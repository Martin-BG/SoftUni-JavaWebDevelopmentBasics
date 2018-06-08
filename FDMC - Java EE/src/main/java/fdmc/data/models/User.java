package fdmc.data.models;

import java.util.Objects;

public final class User {

    private final String username;
    private final String password;
    private final UserRole role;

    public User(final String username, final String password, final UserRole role) {
        this.username = Objects.requireNonNull(username);
        this.password = Objects.requireNonNull(password);
        this.role = Objects.requireNonNull(role);
    }

    public String getUsername() {
        return this.username;
    }

    public boolean isPasswordValid(final String password) {
        return this.password.equals(password);
    }

    public boolean isAdmin() {
        return this.role == UserRole.ADMIN;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
