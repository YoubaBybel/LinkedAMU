package main.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import main.entities.Activity;
import main.entities.User;
import main.services.ActivityManager;
import main.services.UserManager;

@ManagedBean(name = "search")
@SessionScoped
public class SearchController {

    @EJB
    UserManager userManager;

    @EJB
    ActivityManager activityManager;

    public List<User> getAllUsers() {
	return userManager.findAll();
    }

    public User getUser(int id) {
	return userManager.findById(id);
    }

    public List<User> getUser(String nameOrFirstName) {
	List<User> listUsers = new ArrayList<>();
	userManager.findByName(nameOrFirstName).forEach(user -> listUsers.add(user));
	userManager.findByFirstName(nameOrFirstName).forEach(user -> listUsers.add(user));
	return listUsers;
    }

    public List<Activity> getAllActivities() {
	return activityManager.findAll();
    }

    public Activity getActivity(int id) {
	return activityManager.findById(id);
    }

    public List<Activity> getActivity(String title) {
	return activityManager.findByTitle(title);
    }

    /*public List<Activity> getActivity(int year) {
	return activityManager.findByYear(year);
    }*/

    /*public List<Activity> getActivity(Nature nature) {
	return activityManager.findByNature(nature);
    }*/

    /*public List<Activity> getActivity(Object label) {
	List<Activity> list_activities = null;

	try {
	    int intLabel = Integer.parseInt(((String) label).trim());
	    return getActivity(intLabel);
	}
	catch (NumberFormatException nfe) {

	    String stringLabel = ((String) label).toLowerCase();
	    switch (stringLabel) {
		case "a":
		    activityList.addAll(getActivity(Nature.FORMATION));
		    activityList.addAll(getActivity(Nature.STAGE));
		    activityList.addAll(getActivity(Nature.AUTRE));
		    break;
		case "e":
		    activityList.addAll(getActivity(Nature.EXP_PRO));
		    activityList.addAll(getActivity(Nature.EXP_PERSO));
		    activityList.addAll(getActivity(Nature.STAGE));
		    activityList.addAll(getActivity(Nature.AUTRE));
		    break;
		case "f":
		case "i":
		case "m":
		case "n":
		    activityList.addAll(getActivity(Nature.FORMATION));
		    break;
		case "g":
		    activityList.addAll(getActivity(Nature.STAGE));
		    break;
		case "j":
		    activityList.addAll(getActivity(Nature.PROJET));
		    break;
		case "o":
		    activityList.addAll(getActivity(Nature.FORMATION));
		    activityList.addAll(getActivity(Nature.EXP_PERSO));
		    activityList.addAll(getActivity(Nature.EXP_PRO));
		    activityList.addAll(getActivity(Nature.PROJET));
		    break;
		case "p":
		    activityList.addAll(getActivity(Nature.EXP_PERSO));
		    activityList.addAll(getActivity(Nature.EXP_PRO));
		    activityList.addAll(getActivity(Nature.PROJET));
		    break;
		case "r":
		    activityList.addAll(getActivity(Nature.EXP_PERSO));
		    activityList.addAll(getActivity(Nature.EXP_PRO));
		    activityList.addAll(getActivity(Nature.PROJET));
		    activityList.addAll(getActivity(Nature.AUTRE));
		    break;
		case "s":
		    activityList.addAll(getActivity(Nature.EXP_PERSO));
		    activityList.addAll(getActivity(Nature.STAGE));
		    break;
		case "t":
		    activityList.addAll(getActivity(Nature.FORMATION));
		    activityList.addAll(getActivity(Nature.STAGE));
		    activityList.addAll(getActivity(Nature.PROJET));
		    activityList.addAll(getActivity(Nature.AUTRE));
		    break;
		case "x":
		    activityList.addAll(getActivity(Nature.EXP_PERSO));
		    activityList.addAll(getActivity(Nature.EXP_PRO));
		default:
		    return getAllActivities();
		    break;
	    }
	} finally {
	    return activityList;
	}
    }*/
}

