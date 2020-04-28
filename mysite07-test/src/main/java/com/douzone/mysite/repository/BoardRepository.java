package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.BoardVo;

@Repository
public class BoardRepository {
	
	@Autowired
	private SqlSession sqlSession;
	
	public List<BoardVo> findAll() {		
		return sqlSession.selectList( "board.findAll");
	}

	public List<BoardVo> search(String kwd, String radioValue) {
		List<BoardVo> list = new ArrayList<>();

		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			// String sql = "select no, name, contents, password,
			// date_format(reg_date,'%Y-%m-%d %h:%i:%s') from guestbook order by reg_date
			// desc";
			// String sql = "select no, title, contents, hit, reg_date, g_no, o_no, depth,
			// user_no from board";
			if ("conORtit".equals(radioValue)) {
				String sql = "select a.no, title, contents, hit, reg_date, g_no, o_no, depth, user_no, b.name from board a, user b where a.user_no = b.no and (title like '%"
						+ kwd + "%' or contents like '%" + kwd + "%') order by g_no desc, o_no asc";
				pst = conn.prepareStatement(sql);

				rs = pst.executeQuery();

				while (rs.next()) {
					Long no = rs.getLong(1);
					String title = rs.getString(2);
					String contents = rs.getString(3);
					int hit = rs.getInt(4);
					String regDate = rs.getString(5);
					int groupNo = rs.getInt(6);
					int groupOrNo = rs.getInt(7);
					int depth = rs.getInt(8);
					Long userNo = rs.getLong(9);
					String name = rs.getString(10);

					BoardVo vo = new BoardVo();

					vo.setNo(no);
					vo.setTitle(title);
					vo.setContents(contents);
					vo.setHit(hit);
					vo.setRegDate(regDate);
					vo.setGroupNo(groupNo);
					vo.setGroupOrNo(groupOrNo);
					vo.setDepth(depth);
					vo.setUserNo(userNo);
					vo.setName(name);

					list.add(vo);
				}
			} else {

				String sql = "select a.no, title, contents, hit, reg_date, g_no, o_no, depth, user_no, b.name from board a, user b where a.user_no = b.no and name like '%"+kwd+"%'order by g_no desc, o_no asc";
				pst = conn.prepareStatement(sql);

				rs = pst.executeQuery();

				while (rs.next()) {
					Long no = rs.getLong(1);
					String title = rs.getString(2);
					String contents = rs.getString(3);
					int hit = rs.getInt(4);
					String regDate = rs.getString(5);
					int groupNo = rs.getInt(6);
					int groupOrNo = rs.getInt(7);
					int depth = rs.getInt(8);
					Long userNo = rs.getLong(9);
					String name = rs.getString(10);

					BoardVo vo = new BoardVo();

					vo.setNo(no);
					vo.setTitle(title);
					vo.setContents(contents);
					vo.setHit(hit);
					vo.setRegDate(regDate);
					vo.setGroupNo(groupNo);
					vo.setGroupOrNo(groupOrNo);
					vo.setDepth(depth);
					vo.setUserNo(userNo);
					vo.setName(name);

					list.add(vo);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 6. 자원 정리
			try {
				if (rs != null)
					rs.close();
				if (pst != null)
					pst.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return list;
	}
	

	public List<BoardVo> pagingList(int start) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put( "start", start );	
		return sqlSession.selectList( "board.pList", start );
	}
	


	public int delete( Long no ) {
		Map<String, Long> map = new HashMap<String, Long>();
		map.put( "no", no );		
		
		return sqlSession.delete( "board.delete", map );
	}

	public int hit( Long no ) {
		return sqlSession.update( "board.hit", no );
	}
	
	public int updateOrderNo( Integer groupNo, Integer groupOrNo ) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put( "groupNo", groupNo );
		map.put( "groupOrNo", groupOrNo );
		
		return sqlSession.update( "board.updateOrederNo", map );
	}	
	
	public int getTotalCount() {
		return sqlSession.selectOne( "board.totalCount");
	}	

	public int getGno(Long no) {
		return sqlSession.selectOne( "board.findMaxGno");
	}	
	public int findGNo(Long no) {
		Map<String, Long> map = new HashMap<String, Long>();
		map.put( "no", no );
		return sqlSession.selectOne( "board.findGno");
	}		

	public int getMaxGno() {
		return sqlSession.selectOne( "board.findMaxGno");
	}	
	
	public int getOno(Long no) {
		Map<String, Long> map = new HashMap<String, Long>();
		map.put( "no", no );
		return sqlSession.selectOne( "board.findOno");
	}		

	public int insert( BoardVo boardVo ) {
		return sqlSession.insert( "board.insert", boardVo );
	}


	public BoardVo findByNo( Long no ) {
		return sqlSession.selectOne( "board.findByNo", no );
	}

	public int update( BoardVo boardVo ) {
		return sqlSession.update( "board.update", boardVo );
	}
	
	private Connection getConnection() throws SQLException {
		Connection conn = null;
		
		try {

			Class.forName("org.mariadb.jdbc.Driver");

			String url = "jdbc:mysql://192.168.1.98:3307/webdb";
			conn = DriverManager.getConnection(url,"webdb","webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} 
		
		return conn;
	}
}
