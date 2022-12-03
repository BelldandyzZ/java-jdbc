import java.util.HashMap;
import java.util.Scanner;
import java.util.ResourceBundle;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;

public class JDBCTest05{

	public static void main(String[] args){
		HashMap<String,String> userInfo =  initUI();

		boolean isSuccess = checkLogin(userInfo);

		System.out.println(isSuccess ?"µ«¬º≥…π¶":"µ«¬º ß∞‹");
	}

	
	public static boolean checkLogin(HashMap<String,String> userInfo){
		
		boolean flag = false;

		ResourceBundle bundle = ResourceBundle.getBundle("jdbc");

		String driver = bundle.getString("driver");
		String url = bundle.getString("url");
		String pwd = bundle.getString("password");
		String user = bundle.getString("username");

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		String username = userInfo.get("username");

		String password = userInfo.get("password");

		try{
			Class.forName(driver);
			conn = DriverManager.getConnection(url,user,pwd);
			stmt = conn.createStatement();
			String sql = "SELECT username,password FROM user WHERE username='"+username+"' AND password='"+password+"'";
			rs = stmt.executeQuery(sql);
			if(rs.next()){
				flag = true;
			}
		}
		catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		finally{
			if(rs != null){
				try{
					rs.close();
				}catch(SQLException e){
					e.printStackTrace();
				}
			}

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
		return flag;
	}


	public static HashMap<String,String> initUI(){
	
		Scanner input = new Scanner(System.in);
		
		System.out.print("«Î ‰»Î”√ªß√˚");

		String username = input.nextLine();

		System.out.print("«Î ‰»Î√‹¬Î");

		String password = input.nextLine();

		HashMap<String,String> userInfo = new HashMap<String,String>();

		userInfo.put("username",username);

		userInfo.put("password",password);

		return userInfo;
	
	}
}