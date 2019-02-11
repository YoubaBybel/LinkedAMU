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

	@PostConstruct
	public void init() {
		System.out.println("Create " + this);
		if (userM.findAll().isEmpty()) {
			User user1 = new User();
			user1.setName("BERTHOD");
			user1.setFirstName("Timothee");
			user1.setPassword("12345678");
			user1.setEmail("timothee@berthod.net");
			user1.setWebSite("https://google.fr");
			User tim = userM.createUser(user1);

			User user2 = new User("EL YOUSFI", "Ayoub", "youba@darkness.com", "23862386");
			user2.setWebSite("ayoub.elyousfi.free.fr");
			User youba = userM.createUser(user2);

			Activity master2 = new Activity(2019, "FORMATION", "Master 2 - Ingénierie du Logiciel et des Données");
			master2.setWebAddress("http://masterinfo.univ-mrs.fr/master-2018");
			master2.setDescription("J'ai bientôt fini ce master 2, bientôt...");
			activityManager.createActivity(master2);

			Activity animateur = new Activity(2012, "EXP_PERSO", "Animateur Éclaireuses Éclaireurs de France");
			animateur.setDescription("J'ai été animateur pendant 4 ans avant de me concentrer sur mes études");
			activityManager.createActivity(animateur);

			Activity stage = new Activity(2019, "STAGE", "SOGETI Aix");
			stage.setDescription("Mon premier stage en entreprise, ça va être génial !!");
			activityManager.createActivity(stage);

			Activity basket = new Activity(2015, "AUTRE", "Basket en compétition");
			basket.setDescription("J'aimerais bien jouer mais je suis trop nul à ce sport...");
			activityManager.createActivity(basket);

			Activity vendeur = new Activity(2016, "EXP_PRO", "Vendeur & Conseiller");
			vendeur.setDescription("J'ai été vendeur auprès de 3 grandes enseignes dans le monde du prêt-à-porter.\n"
					+ "¤ Le Temps des Cerises\n" + "¤ H&M\n" + "¤ Zara");
			activityManager.createActivity(vendeur);

			tim.addActivity(master2);
			tim.addActivity(stage);
			userM.updateUser(tim);
			youba.addActivity(basket);
			youba.addActivity(vendeur);
			youba.addActivity(animateur);
			userM.updateUser(youba);

			activityManager.findAll().forEach(act -> activityManager.updateActivity(act));
		}
	}

//	@PostConstruct
//	public void init() {
//		User user;
//		Fairy fairy = Fairy.create(Locale.forLanguageTag("fr"));
//		if (userM.findAll().isEmpty()) {
//			for (int i = 0; i < 100_000; i++) {
//				io.codearte.jfairy.producer.person.Person fPerson = fairy.person();
//				user = new User();
//
//				user.setEmail(fPerson.getEmail());
//				user.setFirstName(fPerson.getFirstName());
//				user.setName(fPerson.getLastName());
//				user.setPassword(fPerson.getPassword());
//				if (userM.findByEmail(user.getEmail()) == null)
//					userM.createUser(user);
//			}
//		}
//	}

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
		return "profile";
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
