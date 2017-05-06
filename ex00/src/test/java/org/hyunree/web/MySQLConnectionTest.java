package org.hyunree.web;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;

public class MySQLConnectionTest {

	private static final String DRIVER = 
			"com.mysql.jdbc.Driver";
	private static final String URL = 
			"jdbc:mysql://127.0.0.1:3306/book_ex?serverTimezone=UTC";
	private static final String USER = 
			"hrjin";
	private static final String PW = 
			"920925";
			
	
	@Test
	public void testConnection() throws Exception{
		
		Class.forName(DRIVER);
		
		// AutoCloseable Interface(close()) �� �����Ǿ� try, catch, finally �� ��� ������ �ʿ� ����.
		
		try(Connection con = DriverManager.getConnection(URL, USER, PW)){
			
			System.out.println(con);
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
