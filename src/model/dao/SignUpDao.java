package model.dao;

import java.util.List;

import model.entities.SignUp;

public interface SignUpDao {

	void insert(SignUp obj);
	void update(SignUp obj);
	void deleteById(Integer id);
	SignUp findById(Integer id);
	List<SignUp> findAll();
}
