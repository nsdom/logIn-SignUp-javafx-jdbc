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
					"INSERT INTO sign up "
					+ "(First Name, Last Name, Email, Phone Number, Birth Date, Age, Gender, LogIn_Id) "
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
					"UPDATE sign up "
					+ "SET First Name = ?, Last Name = ?, Email = ?, Phone Number = ?, BirthDate = ?, Age = ?, Gender = ? "
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
	}

	@Override
	public SignUp findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT sign up.*,log in.User Name as LogName "
					+ "FROM sign up INNER JOIN log in "
					+ "ON sign up.Log In_Id = log in.Id "
					+ "WHERE sign up.Id = ?");
			
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
		obj.setFirstName(rs.getString("First Name"));
		obj.setLastName(rs.getString("Last Name"));
		obj.setEmail(rs.getString("Email"));
		obj.setPhoneNumber(rs.getInt("Phone Number"));
		obj.setBirthDate(rs.getDate("Birth Date"));
		obj.setAge(rs.getInt("Age"));
		obj.setGender(rs.getString("Gender"));
		obj.setLogIn(logIn);
	
		return obj;
	}

	private LogIn instantiateLogIn(ResultSet rs) throws SQLException {
		LogIn logIn = new LogIn();
		logIn.setId(rs.getInt("Log In_Id"));
		logIn.setName(rs.getString("LogName"));
		return logIn;
	}

	@Override
	public List<SignUp> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT log in.*,sign up.First Name as logName "
					+ "FROM sign up INNER JOIN log in "
					+ "ON sign up.Log In_Id = log in.Id "
					+ "ORDER BY First Name");
			
			rs = st.executeQuery();
			
			List<SignUp> list = new ArrayList<>();
			Map<Integer, LogIn> map = new HashMap<>();
			
			while (rs.next()) {
				
				LogIn logIn = map.get(rs.getInt("Log In_Id"));
				
				if (logIn == null) {
					logIn = instantiateLogIn(rs);
					map.put(rs.getInt("Log In_Id"), logIn);
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

	@Override
	public List<SignUp> findByLogIn(LogIn logIn) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE DepartmentId = ? "
					+ "ORDER BY Name");
			
			st.setInt(1, department.getId());
			
			rs = st.executeQuery();
			
			List<SignUp> list = new ArrayList<>();
			Map<Integer, LogIn> map = new HashMap<>();
			
			while (rs.next()) {
				
				LogIn dep = map.get(rs.getInt("DepartmentId"));
				
				if (dep == null) {
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				
				SignUp obj = instantiateSeller(rs, dep);
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
