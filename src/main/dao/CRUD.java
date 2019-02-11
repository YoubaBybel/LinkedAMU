package main.dao;

import java.util.List;

public interface CRUD {

    <T> void create(T entity);

    <T> T update(T entity);

    <T> void delete(Integer id, Class<T> aClass);

    <T> T findById(Integer id, Class<T> aClass);

    <T> List<T> findAll(Class<T> aClass);

}
