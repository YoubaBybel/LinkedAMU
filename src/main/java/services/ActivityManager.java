package services;

import entities.Activity;

import java.util.List;

public interface ActivityManager {
    Activity createActivity();

    Activity createActivity(Activity activity);

    Activity updateActivity(Activity activity);

    void removeActivity(int id);

    Activity findById(int id);

    List<Activity> findAll();

    List<Activity> findByYear(int year);

    List<Activity> findByNature(String nature);

    List<Activity> findByTitle(String title);
}
