package main.controllers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import main.entities.Activity;
import main.entities.Activity.Nature;
import main.entities.User;
import main.services.ActivityManager;
import main.services.UserManager;

@ManagedBean(name = "activity")
@SessionScoped
public class ActivityController {

	@EJB
	ActivityManager activityManager;

	@EJB
	UserManager userM;

	Activity currentActivity;
	
	Map<String, Nature> natures = new LinkedHashMap<>();

	@PostConstruct
	public void init() {
		natures.put("NATURE DE L'ACTIVITÉ", Nature.AUTRE);
		natures.put("FORMATION", Nature.FORMATION);
		natures.put("EXPERIENCE PROFESSIONNELLE", Nature.EXP_PRO);
		natures.put("STAGE", Nature.STAGE);
		natures.put("EXPERIENCE PERSONNELLE", Nature.EXP_PERSO);
		natures.put("PROJET", Nature.PROJET);
		natures.put("AUTRE", Nature.AUTRE);
	}
	
//	@PostConstruct
//	public void init() {
//		System.out.println("Create " + this);
//		activityManager.findAll().forEach(activity -> activityManager.removeActivity(activity.getId()));
//
//		Activity master2 = new Activity(2019, "FORMATION", "Master 2 - Ingénierie du Logiciel et des Données");
//		master2.setWebAddress("http://masterinfo.univ-mrs.fr/master-2018");
//		master2.setDescription("J'ai bientôt fini ce master 2, bientôt...");
//		activityManager.createActivity(master2);
//
//		Activity animateur = new Activity(2012, "EXP_PERSO", "Animateur Éclaireuses Éclaireurs de France");
//		animateur.setDescription("J'ai été animateur pendant 4 ans avant de me concentrer sur mes études");
//		activityManager.createActivity(animateur);
//
//		Activity stage = new Activity(2019, "STAGE", "SOGETI Aix");
//		stage.setDescription("Mon premier stage en entreprise, ça va être génial !!");
//		activityManager.createActivity(stage);
//
//		Activity basket = new Activity(2015, "AUTRE", "Basket en compétition");
//		basket.setDescription("J'aimerais bien jouer mais je suis trop nul à ce sport...");
//		activityManager.createActivity(basket);
//
//		Activity vendeur = new Activity(2016, "EXP_PRO", "Vendeur & Conseiller");
//		vendeur.setDescription("J'ai été vendeur auprès de 3 grandes enseignes dans le monde du prêt-à-porter.\n"
//				+ "¤ Le Temps des Cerises\n" + "¤ H&M\n" + "¤ Zara");
//		activityManager.createActivity(vendeur);
//
//		
//	}

	public Map<String, Nature> getNatures() {
		return natures;
	}

	public Activity getCurrentActivity() {
		return currentActivity;
	}

	public List<Activity> getActivities() {
		return activityManager.findAll();
	}

	public Activity getActivity(int id) {
		return activityManager.findById(id);
	}

	public List<Activity> getActivity(String title) {
		return activityManager.findByTitle(title);
	}

	public String createActivity() {
		currentActivity = new Activity();
		return "editActivity";
	}

	public String createActivity(User user) {
		currentActivity = new Activity();
		currentActivity.setUser(user);
		return "editActivity";
	}

	public String editActivity(int id) {
		currentActivity = activityManager.findById(id);
		return "editActivity";
	}

	public String removeActivity(Integer id) {
	    if(id == null) {
	        activityManager.removeActivity(currentActivity.getId());
        }
        else {
            activityManager.removeActivity(id);
        }
		return "activities";
	}

	public String saveActivity() {
		if (currentActivity.getId() == null) {
			currentActivity = activityManager.createActivity(currentActivity);
		} else {
			currentActivity = activityManager.updateActivity(currentActivity);
		}
		return showActivity(currentActivity.getId());
	}

	public String showActivity(Integer id) {
	    if(id == null) {
	        currentActivity = activityManager.findById(currentActivity.getId());
        }
        else {
            currentActivity = activityManager.findById(id);
        }
		return "showActivity";
	}
}
