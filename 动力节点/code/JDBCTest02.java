import com.mysql.jdbc.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;

public class JDBCTest02{
	
	public static final String url = "jdbc:mysql://localhost:3306/mydb";
	public static final String username = "root";
	public static final String password = "root";

	public static void main(String[] args){
		
		Connection conn = null;
		Statement stmt = null;
		try{
			//1.注册驱动
			Driver driver = new com.mysql.jdbc.Driver();
			DriverManager.registerDriver(driver);

			//2.获取连接
			conn = DriverManager.getConnection(url,username,password);
			System.out.println(conn);

			//3.获取数据库操作对象
			stmt = conn.createStatement();

			//4.执行sql
			String sql =  "insert into emp(name,age) values('Bob',18);";
			
			int count = stmt.executeUpdate(sql);
			
			String result = count == 1? "添加成功":"添加失败";

			System.out.println(result);
			
			//5.处理查询结果集

		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			//6.释放资源。

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