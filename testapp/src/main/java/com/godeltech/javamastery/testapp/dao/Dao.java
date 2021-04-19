package com.godeltech.javamastery.testapp.dao;

import java.util.List;

public interface Dao<T> {

	T get(long id);

	List<T> getAll();

	void save(T t);

	void update(T t);

	void delete(long id);
}