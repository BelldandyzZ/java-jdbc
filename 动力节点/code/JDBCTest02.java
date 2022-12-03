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
			//1.ע������
			Driver driver = new com.mysql.jdbc.Driver();
			DriverManager.registerDriver(driver);

			//2.��ȡ����
			conn = DriverManager.getConnection(url,username,password);
			System.out.println(conn);

			//3.��ȡ���ݿ��������
			stmt = conn.createStatement();

			//4.ִ��sql
			String sql =  "insert into emp(name,age) values('Bob',18);";
			
			int count = stmt.executeUpdate(sql);
			
			String result = count == 1? "��ӳɹ�":"���ʧ��";

			System.out.println(result);
			
			//5.�����ѯ�����

		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			//6.�ͷ���Դ��

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