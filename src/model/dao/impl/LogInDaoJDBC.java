package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import db.DbIntegrityException;
import model.dao.LogInDao;
import model.entities.LogIn;

public class LogInDaoJDBC implements LogInDao {

	private Connection conn;
	
	public LogInDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public LogIn findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT * FROM login WHERE Id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				LogIn obj = new LogIn();
				obj.setId(rs.getInt("Id"));
				obj.setUsername(rs.getString("Username"));
				obj.setPassword("Not available");
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

	@Override
	public List<LogIn> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT * FROM login ORDER BY Username");
			rs = st.executeQuery();

			List<LogIn> list = new ArrayList<>();

			while (rs.next()) {
				LogIn obj = new LogIn();
				obj.setId(rs.getInt("Id"));
				obj.setUsername(rs.getString("Username"));
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
	public void insert(LogIn obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"INSERT INTO login " +
				"(Username, Password) " +
				"VALUES " +
				"(?, ?)", 
				Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getUsername());
			st.setString(2, obj.getPassword());

			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
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
	public void update(LogIn obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"UPDATE login " +
				"SET Username = ?, Password = ? " +
				"WHERE Id = ?");

			st.setString(1, obj.getUsername());
			st.setString(2, obj.getPassword());
			st.setInt(3, obj.getId());

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
	public void deleteById(int id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"DELETE FROM login WHERE Id = ?");

			st.setInt(1, id);

			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
		}
	}
}
