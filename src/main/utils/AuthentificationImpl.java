package main.utils;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateful;

import main.entities.User;
import main.services.UserManager;

@Stateful
public class AuthentificationImpl implements Authentification {

	private boolean isLogged = false;

	@EJB
	UserManager userM;

	@Override
	public User login(User user) {
		isLogged = false;
		List<User> userLogged;
		userLogged = userM.findByLogin(user.getEmail(), user.getPassword());
		if (userLogged.isEmpty()) {
			return null;
		} else {
			isLogged = true;
			return userLogged.get(0);
		}
	}

	@Override
	public void logout() {
		isLogged = false;
		System.out.printf("Logout on %s\n", this);
	}

	@Override
	public boolean isLogged() {
		return isLogged;
	}
}
