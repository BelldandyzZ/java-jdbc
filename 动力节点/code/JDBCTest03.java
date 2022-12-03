
import java.util.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;

public class JDBCTest03{

	public static void main(String[] args){
	
		ResourceBundle bundle = ResourceBundle.getBundle("jdbc");

		String driver = bundle.getString("driver");

		String url = bundle.getString("url");

		String username = bundle.getString("username");

		String password = bundle.getString("password");

		Connection conn = null;

		Statement stmt =  null;

		try{
			Class.forName(driver);
			
			conn = DriverManager.getConnection(url,username,password);
		
			stmt = conn.createStatement();

			String sql = "insert into emp(name,age,gender) values('cccccc',10,true)";

			int count = stmt.executeUpdate(sql);

			System.out.println(count == 1 ? "添加成功" :"添加失败");

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