package model.dao;

import db.DB;
import model.dao.impl.LogInDaoJDBC;
import model.dao.impl.SignUpDaoJDBC;

public class DaoFactory {

	public static SignUpDao createSellerDao() {
		return new SignUpDaoJDBC(DB.getConnection());
	}
	
	public static LogInDao createDepartmentDao() {
		return new LogInDaoJDBC(DB.getConnection());
	}
}
