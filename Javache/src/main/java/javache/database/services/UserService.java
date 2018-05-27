package javache.database.services;

import javache.database.entities.User;
import javache.database.repositories.UserRepository;
import javache.database.util.CsvFileManager;

import java.util.Collection;
import java.util.UUID;

public class UserService {

    private final UserRepository userRepository;

    public UserService() {
        this.userRepository = new UserRepository(new CsvFileManager());
    }

    public boolean registerUser(final String name, final String password) {
        User user = new User(UUID.randomUUID().toString(), name, password);
        return this.userRepository.add(user);
    }

    public User findByName(final String name) {
        return this.userRepository.find(name);
    }

    public Collection<User> getAll() {
        return this.userRepository.getAll();
    }
}
