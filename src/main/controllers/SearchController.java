package main.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
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
	
	private List<User> searchUsers;
	private List<Activity> searchActivities;
	private String input = "";

	@PostConstruct
	public void init() {
		System.out.println("Create " + this);
	}

	public List<User> getSearchUsers() {
		return searchUsers;
	}
	
	public List<Activity> getSearchActivities() {
		return searchActivities;
	}
	
	public String getInput() {
		return input;
	}
	
	public void setInput(String input) {
		this.input = input;
	}
	
	public String search() {
		searchUsers = new ArrayList<User>();
		searchActivities = new ArrayList<Activity>();
		searchUsers.addAll(userManager.findByName(input));
		searchUsers.addAll(userManager.findByFirstName(input));
		searchActivities.addAll(activityManager.findByTitle(input));
		input = "";
		return "home";
	}
}
