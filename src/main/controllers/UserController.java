package main.controllers;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import main.entities.User;
import main.services.UserManager;
import main.utils.Authentification;

@ManagedBean(name = "user")
@SessionScoped
public class UserController {

	@EJB
	UserManager userM;

	@EJB
	Authentification auth;

	private User user = new User();
	private boolean isLogged = false;
	private User userLogged;

	@PostConstruct
	public void init() {
		System.out.println("Create " + this);
		if (userM.findAll().isEmpty()) {
			User user1 = new User();
			user1.setName("BERTHOD");
			user1.setFirstName("Timothee");
			user1.setPassword("123");
			user1.setEmail("timothee@berthod.net");
			user1.setWebSite("https://google.fr");
			userM.createUser(user1);
		}
	}

	@PreDestroy
	public void end() {
		userM.findAll().forEach(userToRemove -> {
			userM.removeUser(userToRemove.getId());
			System.out.println("to remove " + userToRemove);
		});
	}

	public List<User> getUsers() {
		return userM.findAll();
	}

	public User getUser() {
		return user;
	}

	public User getUserLogged() {
		return userLogged;
	}

	public boolean isLogged() {
		return isLogged;
	}

	public String findUser(int id) {
		user = userM.findById(id);
		return "userPage";
	}

	public String addUser() {
		userM.createUser(user);
		return "userPage";
	}

	public String updateUser() {
		userM.updateUser(user);
		return "userPage";
	}

	public String newUser() {
		user = new User();
		return null;
	}

	public String login() {
		String page;
		userLogged = auth.login(user);
		isLogged = auth.isLogged();
		if (isLogged) {
			page = "profile";
		} else {
			page = "home";
		}
		return page;
	}

	public String logout() {
		auth.logout();
		isLogged = false;
		userLogged = null;
		return "home";
	}
}
