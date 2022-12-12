package ch07;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class PlayerDao {
	public static Connection getConnection() {
		Connection conn;
		try {
			Context initContext = new InitialContext();
			DataSource ds = (DataSource) initContext.lookup("java:comp/env/jdbc/world");
			conn = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return conn;
	}
	
	public void deletePlayer(int backNo) {
		Connection conn = getConnection();
		String sql = "UPDATE player SET isDeleted=1	WHERE backNo=?;";
		try {
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, backNo);
			
			// Delete - Update 실행
			pStmt.executeUpdate();
			pStmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updatePlayer(Player p) {
		Connection conn = getConnection();
		String sql = "UPDATE player SET name=?, position=?, birthDay=?, height=?, isDeleted=?"
				+ "	WHERE backNo=?;";
		try {
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, p.getName());
			pStmt.setString(2, p.getPosition().toString());
			pStmt.setString(3, p.getBirthDay().toString());
			pStmt.setInt(4, p.getHeight());
			pStmt.setInt(5, p.getIsDeleted());
			pStmt.setInt(6, p.getBackNo());
			
			// Update 실행
			pStmt.executeUpdate();
			pStmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Player getPlayer(int backNo) {
		Connection conn = getConnection();
		String sql = "SELECT * FROM player WHERE backNo=?;";
		Player p = new Player();
		try {
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, backNo);
			
			// Select 실행
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				p.setBackNo(rs.getInt(1));
				p.setName(rs.getString(2));
				p.setPosition(Position.valueOf(rs.getString(3)));
				p.setBirthDay(LocalDate.parse(rs.getString(4)));
				p.setHeight(rs.getInt(5));
				p.setIsDeleted(rs.getInt(6));
			}
			rs.close();
			pStmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return p;
	}
	
	public List<Player> getPlayers() {
		Connection conn = getConnection();
		List<Player> list = new ArrayList<>();
		String sql = "SELECT * FROM player WHERE isDeleted = 0"
				+ "	ORDER BY FIELD(`position`,'투수','포수','내야수','외야수');";
		try {
			Statement stmt = conn.createStatement();
			
			// Select 실행
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Player p = new Player();
				p.setBackNo(rs.getInt(1));
				p.setName(rs.getString(2));
				p.setPosition(Position.valueOf(rs.getString(3)));
				p.setBirthDay(LocalDate.parse(rs.getString(4)));
				p.setHeight(rs.getInt(5));
				p.setIsDeleted(rs.getInt(6));
				list.add(p);
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return list;
	}
	
	public void insertPlayer(Player p) {
		Connection conn = getConnection();
		String sql = "INSERT INTO player VALUES (?, ?, ?, ?, ?, DEFAULT);";
		try {
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, p.getBackNo());
			pStmt.setString(2, p.getName());
			pStmt.setString(3, p.getPosition().toString());
			pStmt.setString(4, p.getBirthDay().toString());
			pStmt.setInt(5, p.getHeight());
			
			pStmt.executeUpdate();
			pStmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public void createTable() {
		Connection conn = getConnection();
		String sql = "" + 
				"CREATE TABLE if NOT EXISTS player (\n"
				+ "	backNo INT PRIMARY KEY,\n"
				+ "	name VARCHAR(8) NOT NULL,\n"
				+ "	position VARCHAR(4) NOT NULL,\n"
				+ "	birthDay DATE,\n"
				+ "	height INT,\n"
				+ "	isDeleted INT DEFAULT 0\n"
				+ ");";
		try {
			Statement stmt = conn.createStatement();
			
			stmt.execute(sql);
			stmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
