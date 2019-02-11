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
