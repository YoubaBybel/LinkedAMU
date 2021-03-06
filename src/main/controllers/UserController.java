package main.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import io.codearte.jfairy.Fairy;
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

	@PostConstruct
	public void init() {
		System.out.println("Create " + this);
		User user1;
		Fairy fairy = Fairy.create(Locale.forLanguageTag("fr"));
		while (userM.findAll().size() < 100) {
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

	public String addUser() {
		String page;
		userM.createUser(user);
		if(isLogged) {
			page = "home";
		} else {
			page = "login";
		}
		return page;
	}

	public void updateUser() {
		userLogged = userM.updateUser(userLogged);
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
			page = "home";
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
		user.setCv(activityManager.findUserActivities(user));
		user = userM.updateUser(user);
		return "cv";
	}

	public void getActivities(User user) {
		user.setCv(activityManager.findUserActivities(user));
	}

	public String removeCV() {
		userLogged.getCv().forEach(activity -> {
			activityManager.removeActivity(activity.getId());
		});
		userLogged.setCv(new ArrayList<>());
		userM.updateUser(userLogged);
		return "home";
	}
}
