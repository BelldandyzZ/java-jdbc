
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
	����url

	jdbc:mysql://localhost:3306/atguigudb = Э�� + ip + �˿� + ��Դ��
	
	MySQL��һ�����ݿ��������atguigudb������ݿ�ֻ�Ǹ÷������ϵ�һ����Դ��

	��ʾ�Ҵ�IP��ַ�ǡ�localhost���ļ�����ϵġ�3306���˿ڶ��ڵ������Ȼ���ҵ��������µ�atguigudb�����Դ��

	*/


	/*
	ע�⣺
	ʹ��MySQL����8.0��Ҫ��url�������ʱ�������޸�my.ini�ļ���������ʹ�õ�������8.0

	[mysqld]
	default-time_zone='+8:00'
	
	--------------------------------------------------------------------------------

	ʹ�ü��±�������Ҫ�ڻ�����������classpath

	classpath = .;+����·����ע�����·����Ҫ��������Ҳ����jar������
	*/
	public static void main(String[] args){
		
		Connection conn = null;
		Statement stmt = null;
		try{
			//1.ע������
			Driver driver = new com.mysql.jdbc.Driver();
			DriverManager.registerDriver(driver);
			
			//2.��ȡ����
			conn = DriverManager.getConnection(url,username,password);

			//3.��ȡ�������ݿ����
			stmt = conn.createStatement();

			//4.ִ��sql
			String sql = "insert into emp(name,age) values('marry',18)";

			//�÷���ִ��`INSERT`��`DELETE`��`UPDATE`��䣬����ֵ�����ݿ��б�Ӱ��ļ�¼��������
			int count = stmt.executeUpdate(sql);

			if(count > 0){
				System.out.println("��ӳɹ�");
			}
			else{
				System.out.println("���ʧ��");
			}
			
			//5.�����ѯ�����

		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			//6.�ͷ���Դ
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