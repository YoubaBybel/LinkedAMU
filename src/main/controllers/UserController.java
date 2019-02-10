package main.controllers;

import java.util.ArrayList;
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
	private User userLogged;
	private boolean isLogged = false;

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

			User user2 = new User("EL YOUSFI", "Ayoub", "youba@darkness.com", "2386");
			user2.setWebSite("ayoub.elyousfi.free.fr");
			userM.createUser(user2);
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
		return "showUser.xhtml?faces-redirect=true";
	}

	
	
	public List<User> findUsers(String nameOrFirstName) {
		List<User> listUsers = new ArrayList<>();
		userM.findByName(nameOrFirstName).forEach(user -> listUsers.add(user));
		userM.findByFirstName(nameOrFirstName).forEach(user -> listUsers.add(user));
		return listUsers;
	}

	public String addUser() {
		userM.createUser(user);
		return "profile";
	}

	public void updateUser() {
		userM.updateUser(userLogged);
	}

	public String removeUser() {
		userM.removeUser(user.getId());
		return "index";
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
			page = null;
		}
		return page;
	}

	public String logout() {
		auth.logout();
		isLogged = false;
		userLogged = null;
		user = new User();
		return "index.xhtml?faces-redirect=true";
	}
}
