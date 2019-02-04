package services;

import entities.User;

import java.util.List;

public interface UserManager {
    User createUser();

    User createUser(User user);

    User updateUser(User user);

    void removeUser(int id);

    User findById(int id);

    List<User> findAll();

    List<User> findByName(String name);

    List<User> findByFirstName(String firstName);
}
