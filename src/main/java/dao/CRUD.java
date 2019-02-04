package dao;

import java.util.List;

public interface CRUD {

    <T> T create(T entity);

    <T> T update(T entity);

    <T> void delete(Object id, Class<T> aClass);

    <T> T findById(int id, Class<T> aClass);

    <T> List<T> findAll(Class<T> aClass);

}
