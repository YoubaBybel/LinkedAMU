package main.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import io.codearte.jfairy.Fairy;
import main.entities.Activity;
import main.entities.User;
import main.services.ActivityManager;
import main.services.UserManager;
import main.utils.Authentification;

@ManagedBean(name = "user")
@SessionScoped
public class UserController {

	@EJB
	UserManager userM;

	@EJB
	ActivityManager activityManager;

	@EJB
	Authentification auth;

	private User user = new User();
	private User userLogged;
	private boolean isLogged = false;

	// @PostConstruct
	// public void init() {
	// System.out.println("Create " + this);
	// if (userM.findAll().isEmpty()) {
	// User user1 = new User();
	// user1.setName("BERTHOD");
	// user1.setFirstName("Timothee");
	// user1.setPassword("12345678");
	// user1.setEmail("timothee@berthod.net");
	// user1.setWebSite("https://google.fr");
	// userM.createUser(user1);
	//
	// User user2 = new User("EL YOUSFI", "Ayoub", "youba@darkness.com",
	// "23862386");
	// user2.setWebSite("ayoub.elyousfi.free.fr");
	// userM.createUser(user2);
	// }
	// }

	@PostConstruct
	public void init() {
		System.out.println("Create " + this);
		User user1;
		Fairy fairy = Fairy.create(Locale.forLanguageTag("fr"));
		if (userM.findAll().isEmpty()) {
			for (int i = 0; i < 10; i++) {
				io.codearte.jfairy.producer.person.Person fPerson = fairy.person();
				user1 = new User();
				user1.setEmail(fPerson.getEmail());
				user1.setFirstName(fPerson.getFirstName());
				user1.setName(fPerson.getLastName());
				user1.setPassword(fPerson.getPassword());
				if (userM.findByEmail(user1.getEmail()).isEmpty()) {
					userM.createUser(user1);
				}
			}
		}
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
		return "showUser";
	}

	public List<User> findUsers(String nameOrFirstName) {
		List<User> listUsers = new ArrayList<>();
		listUsers.addAll(userM.findByName(nameOrFirstName));
		listUsers.addAll(userM.findByFirstName(nameOrFirstName));
		return listUsers;
	}

	public String addUser() {
		userM.createUser(user);
		return "home";
	}

	public String updateUser() {
		userLogged = userM.updateUser(userLogged);
		return "profile";
	}

	public String editUser() {
		return "profile";
	}

	public String removeUser(int id) {
		userM.removeUser(id);
		return "home";
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
		return "home";
	}

	public String showCv(int id) {
		user = userM.findById(id);
		return "cv";
	}

	public void getActivities(User user) {
		user.setCv(activityManager.findUserActivities(user));
	}
}
