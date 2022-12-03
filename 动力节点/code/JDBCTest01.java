
import com.mysql.jdbc.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;

public class JDBCTest01{
	
	public static final String username = "root";
	public static final String password = "root";
	public static final String url = "jdbc:mysql://localhost:3306/mydb";


	/*
	关于url

	jdbc:mysql://localhost:3306/atguigudb = 协议 + ip + 端口 + 资源名
	
	MySQL是一个数据库服务器，atguigudb这个数据库只是该服务器上的一个资源。

	表示找打IP地址是‘localhost’的计算机上的‘3306’端口对于的软件，然后找到这个软件下的atguigudb这个资源。

	*/


	/*
	注意：
	使用MySQL驱动8.0需要在url后面添加时区或者修改my.ini文件，我这里使用的是驱动8.0

	[mysqld]
	default-time_zone='+8:00'
	
	--------------------------------------------------------------------------------

	使用记事本开发需要在环境变量配置classpath

	classpath = .;+驱动路径（注意的是路径需要加上驱动也就是jar包名）
	*/
	public static void main(String[] args){
		
		Connection conn = null;
		Statement stmt = null;
		try{
			//1.注册驱动
			Driver driver = new com.mysql.jdbc.Driver();
			DriverManager.registerDriver(driver);
			
			//2.获取连接
			conn = DriverManager.getConnection(url,username,password);

			//3.获取操作数据库对象
			stmt = conn.createStatement();

			//4.执行sql
			String sql = "insert into emp(name,age) values('marry',18)";

			//该方法执行`INSERT`、`DELETE`、`UPDATE`语句，返回值是数据库中被影响的记录总条数。
			int count = stmt.executeUpdate(sql);

			if(count > 0){
				System.out.println("添加成功");
			}
			else{
				System.out.println("添加失败");
			}
			
			//5.处理查询结果集

		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			//6.释放资源
			if(stmt != null){
				try{
					stmt.close();	
				}catch(SQLException e){
					e.printStackTrace();
				}
				
			}

			if(conn != null){
				try{
					stmt.close();	
				}catch(SQLException e){
					e.printStackTrace();
				}
				
			}
		}
		
		
		
		
		

	
	}


}