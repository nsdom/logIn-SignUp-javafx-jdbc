package model.dao;

import java.util.List;

import model.entities.LogIn;

public interface LogInDao {

	void insert(LogIn obj);
	void update(LogIn obj);
	void deleteById(int id);
	LogIn findById(Integer id);
	List<LogIn> findAll();

}
