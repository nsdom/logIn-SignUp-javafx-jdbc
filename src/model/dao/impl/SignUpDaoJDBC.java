package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysql.jdbc.Statement;

import db.DB;
import db.DbException;
import model.dao.SignUpDao;
import model.entities.LogIn;
import model.entities.SignUp;

public class SignUpDaoJDBC implements SignUpDao {

	private Connection conn;
	
	public SignUpDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(SignUp obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO signup "
					+ "(Firstname, Lastname, Email, Phone, BirthDate, Age, Gender, login_Id) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getFirstName());
			st.setString(2, obj.getLastName());
			st.setString(3, obj.getEmail());
			st.setInt(4, obj.getPhoneNumber());
			st.setDate(5, new java.sql.Date(obj.getBirthDate().getTime()));
			st.setInt(6, obj.getAge());
			st.setString(7, obj.getGender());
			st.setInt(8, obj.getLogIn().getId());
			
			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			}
			else {
				throw new DbException("Unexpected error! No rows affected!");
			}
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(SignUp obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE signup "
					+ "SET Firstname = ?, Lastname = ?, Email = ?, Phone = ?, BirthDate = ?, Age = ?, Gender = ? "
					+ "WHERE Id = ?");
			
			st.setString(1, obj.getFirstName());
			st.setString(2, obj.getLastName());
			st.setString(3, obj.getEmail());
			st.setInt(4, obj.getPhoneNumber());
			st.setDate(5, new java.sql.Date(obj.getBirthDate().getTime()));
			st.setInt(6, obj.getAge());
			st.setString(7, obj.getGender());
			st.setInt(8, obj.getId());
			
			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}
	/*
	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM sign up WHERE Id = ?");
			
			st.setInt(1, id);
			
			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}*/

	@Override
	public SignUp findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT signup.*,login.Username as LogName "
					+ "FROM signup INNER JOIN login "
					+ "ON signup.login_Id = login.Id "
					+ "WHERE signup.Id = ?");
			
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				LogIn logIn = instantiateLogIn(rs);
				SignUp obj = instantiateSignUp(rs, logIn);
				return obj;
			}
			return null;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	private SignUp instantiateSignUp(ResultSet rs, LogIn logIn) throws SQLException {
		SignUp obj = new SignUp();
		obj.setId(rs.getInt("Id"));
		obj.setFirstName(rs.getString("Firstname"));
		obj.setLastName(rs.getString("Lastname"));
		obj.setEmail(rs.getString("Email"));
		obj.setPhoneNumber(rs.getInt("Phone"));
		obj.setBirthDate(rs.getDate("BirthDate"));
		obj.setAge(rs.getInt("Age"));
		obj.setGender(rs.getString("Gender"));
		obj.setLogIn(logIn);
	
		return obj;
	}

	private LogIn instantiateLogIn(ResultSet rs) throws SQLException {
		LogIn logIn = new LogIn();
		logIn.setId(rs.getInt("login_Id"));
		logIn.setUsername(rs.getString("LogName"));
		return logIn;
	}

	@Override
	public List<SignUp> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT login.*,signup.Firstname as logName "
					+ "FROM signup INNER JOIN login "
					+ "ON signup.login_Id = login.Id "
					+ "ORDER BY Firstname");
			
			rs = st.executeQuery();
			
			List<SignUp> list = new ArrayList<>();
			Map<Integer, LogIn> map = new HashMap<>();
			
			while (rs.next()) {
				
				LogIn logIn = map.get(rs.getInt("login_Id"));
				
				if (logIn == null) {
					logIn = instantiateLogIn(rs);
					map.put(rs.getInt("login_Id"), logIn);
				}
				
				SignUp obj = instantiateSignUp(rs, logIn);
				list.add(obj);
			}
			return list;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
}
