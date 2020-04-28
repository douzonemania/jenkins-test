package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.douzone.mysite.exception.GuestbookRepositoryException;
import com.douzone.mysite.vo.GuestBookVo;
@Repository
public class GuestBookRepository {
	public List<GuestBookVo> findAll(){
		List<GuestBookVo> list = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			String sql = "select no, name, contents, password, date_format(reg_date,'%Y-%m-%d %h:%i:%s')  from guestbook order by reg_date desc";
			pst = conn.prepareStatement(sql);
			
			rs = pst.executeQuery();
			
			while(rs.next()) {
				Long no = rs.getLong(1);
				String name = rs.getString(2);
				String contents = rs.getString(3);
				String password = rs.getString(4);
				String regDate = rs.getString(5);
				
				GuestBookVo vo = new GuestBookVo();
				
				vo.setNo(no);
				vo.setName(name);
				vo.setContents(contents);
				vo.setPassword(password);
				vo.setRegDate(regDate);
				
				list.add(vo);
			}
		} catch (SQLException e) {
			throw new GuestbookRepositoryException(e.getMessage());
		} finally {
			// 6. 자원 정리
			try {
				if(rs != null)
					rs.close();
				if(pst != null)
					pst.close();
				if(conn !=null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}
	
	public boolean insert(GuestBookVo vo) {
		Connection conn = null;
		PreparedStatement pst = null;
		Boolean result = false;
		
		try {
			conn = getConnection();
			
			String sql = "insert into guestbook values(null, ?, ?, ?,sysdate())";
			pst = conn.prepareStatement(sql);
			
			pst.setString(1, vo.getName());
			pst.setString(2, vo.getContents());
			pst.setString(3, vo.getPassword());
			
			int count = pst.executeUpdate();
			
			result = count == 1;
		} catch (SQLException e) {
			throw new GuestbookRepositoryException(e.getMessage());
		} finally {
			try {
				if(pst != null)
					pst.close();
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public Boolean delete(GuestBookVo vo) {
		Connection conn = null;
		PreparedStatement pst = null;
		Boolean result = false;
		
		try {
			conn = getConnection();
			
			String sql = "delete from guestbook where no = ? and password = ?";
			pst = conn.prepareStatement(sql);
			
			pst.setLong(1, vo.getNo());
			pst.setString(2, vo.getPassword());
			
			int count = pst.executeUpdate();
			result = count == 1;
			
		} catch(SQLException e) {
			throw new GuestbookRepositoryException(e.getMessage());
		} finally {
			// 6. 자원 정리
			try {
				if(pst != null)
					pst.close();
				if(conn !=null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	private Connection getConnection() throws SQLException {
		Connection conn = null;
		
		try {

			Class.forName("org.mariadb.jdbc.Driver");

			String url = "jdbc:mysql://192.168.1.98:3307/webdb";
			conn = DriverManager.getConnection(url,"webdb","webdb");
		} catch (ClassNotFoundException e) {
			throw new GuestbookRepositoryException(e.getMessage());
		} 
		
		return conn;
	}
}