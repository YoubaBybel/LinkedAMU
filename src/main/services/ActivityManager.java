package main.services;

import java.util.List;

import main.entities.Activity;
import main.entities.Activity.Nature;
import main.entities.User;

public interface ActivityManager {

    void createActivity();

    void createActivity(Activity activity);

    Activity updateActivity(Activity activity);

    void removeActivity(int id);

    Activity findById(int id);

    List<Activity> findAll();

    List<Activity> findByYear(int year);

    List<Activity> findByNature(Nature nature);

    List<Activity> findByTitle(String title);

    List<Activity> findUserActivities(User user);

}
