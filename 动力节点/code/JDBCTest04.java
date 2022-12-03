
import java.util.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;

public class JDBCTest04{

	public static void main(String[] args){
	
		ResourceBundle bundle = ResourceBundle.getBundle("jdbc");

		String driver = bundle.getString("driver");

		String url = bundle.getString("url");

		String username = bundle.getString("username");

		String password = bundle.getString("password");

		Connection conn = null;

		Statement stmt =  null;

		ResultSet rs = null;

		try{
			Class.forName(driver);
			
			conn = DriverManager.getConnection(url,username,password);
		
			stmt = conn.createStatement();

			String sql = "SELECT name,age,gender FROM emp";

			rs = stmt.executeQuery(sql);


			while(rs.next()){
			
				String	name = rs.getString("name");

				int age = rs.getInt("age");

				boolean gender = rs.getBoolean("gender");
			
				System.out.println(name + "\t" + age + "\t" + gender);
			}


		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			if(stmt != null){
				try{
					stmt.close();
				}catch(SQLException e){
					e.printStackTrace();
				}			

			}

			if(conn != null){
				try{
					conn.close();
				}catch(SQLException e){
					e.printStackTrace();
				}			

			}
			
		}
	
	}

}