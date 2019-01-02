package model.dao;

import java.util.List;

import model.entities.LogIn;
import model.entities.SignUp;

public interface SignUpDao {

	void insert(SignUp obj);
	void update(SignUp obj);
	void deleteById(Integer id);
	SignUp findById(Integer id);
	List<SignUp> findAll();
	List<SignUp> findByDepartment(LogIn department);
}
