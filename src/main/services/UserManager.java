package main.services;

import java.util.List;

import main.entities.User;

public interface UserManager {

    void createUser();

    void createUser(User user);

    User updateUser(User user);

    void removeUser(int id);

    User findById(int id);

    List<User> findAll();

    List<User> findByName(String name);

    List<User> findByFirstName(String firstName);
    
    List<User> findByEmail(String email);

    List<User> findByLogin(String email, String password);

    //User save(User user);

}
