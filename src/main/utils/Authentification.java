package main.utils;

import main.entities.User;

public interface Authentification {

	public void login(User user);

	public void logout();

	public boolean isLogged();

}
