package main.utils;

import main.entities.User;

public interface Authentification {

	User login(User user);

	void logout();

	boolean isLogged();

}
