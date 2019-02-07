package main.utils;

import javax.ejb.EJB;
import javax.ejb.Remove;
import javax.ejb.Stateful;

import main.entities.User;
import main.services.UserManager;

@Stateful
public class AuthentificationImpl implements Authentification {

	private boolean isLogged = false;

	@EJB
	UserManager userM;

	@Override
	public void login(User user) {
		isLogged = false;
		if (userM.findByLogin(user.getEmail(), user.getPassword()) != null) {
			isLogged = true;
			System.out.printf("Login user %s on %s\n", isLogged, this);
		}
	}

	@Override
	@Remove
	public void logout() {
		isLogged = false;
		System.out.printf("Logout on %s\n", this);
	}

	@Override
	public boolean isLogged() {
		return isLogged;
	}
}
