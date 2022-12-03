[TOC]



# 一、JDBC是什么

![image-20211205172623947](C:\Users\旺财飞飞飞\AppData\Roaming\Typora\typora-user-images\image-20211205172623947.png)

- JDBC就是一套接口`interface`。是JavaEE十三个规范之一，只不过JDBC规范可以在JavaSE的JDK中看见`java.sql.*`

- MySQL，Oracle等数据库厂家按照JDBC的规范去写实现类。这一套实现类就是`驱动`。

- Java程序员面向JDBC这套接口写代码，不需要关心数据库厂商的对JDBC是怎么实现的，也不需要关系使用的是什么数据库，这样就降低了程序的耦合度提高了扩展力。使用MySQL数据库的时候就导入MySQL的驱动，使用Oracle数据库就导入Oralce的驱动。

  

# 二、JDBC编程六步

①注册驱动

- 告诉JVM连接的是那一个品牌的数据库

②获取连接

- JVM进程和数据库进程打开可以进行通讯了。

③获取数据库操作对象

- 专门执行sql语句的对象

④执行sql语句

- 执行DQL，DML，DCL
- JDBC的sql语句不需要写分号

⑤处理查询结果集

- 只有当执行的是SELECT语句的时候才有这一步

⑥释放资源

- 从小到大依次关闭

```java

import com.mysql.jdbc.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;

public class JDBCTest{
	
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
```

# 三、使用类加载和配置文件完成JDBC

```java

/*
jdbc.properties配置文件信息

driver=com.mysql.jdbc.Driver
url=jdbc:mysql://localhost:3306/mydb 
username=root
password=root

*/


import java.util.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;

public class JDBCTest03{

	public static void main(String[] args){
		//获取properties配置文件
		ResourceBundle bundle = ResourceBundle.getBundle("jdbc");
		
        //通过配置文件的key获取对于的value
		String driver = bundle.getString("driver");

		String url = bundle.getString("url");

		String username = bundle.getString("username");

		String password = bundle.getString("password");

		Connection conn = null;

		Statement stmt =  null;

		try{
            
            /*
            在driver对应的com.jdbc.mysql.Driver这个类中
            已经有一个静态块写了注册驱动的代码‘DriverManager.registerDriver(driver);’
            所以我们只需要使用'Class.forName(driver)'让这个类加载即可，静态代码块是类加载的时候执行。
            */
			Class.forName(driver);
			
            //获取连接
			conn = DriverManager.getConnection(url,username,password);
			
            //获取操作数控对象
			stmt = conn.createStatement();
		
            //执行sql
			String sql = "insert into emp(name,age) values('cc',10)";

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
```

# 四、处理查询结果集

```java
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

			/*
			
			执行 SELECT name,age,gender FROM emp;得到的结果		
            +--------+------+--------+
            | name   | age  | gender |
            +--------+------+--------+
            | marry  |   18 |      0 |
            | Bob    |   18 |      0 |
            | Bob    |   18 |      0 |
            | Bob    |   18 |      0 |
            | Bob    |   18 |      0 |
            | Bob    |   18 |      0 |
            | Bob    |   18 |      0 |
            | cc     |   10 |      0 |
            | cc     |   10 |      0 |
            | cc     |   10 |      1 |
            | cccccc |   10 |      1 |
            +--------+------+--------+
            
            
    		rs.next():每调用一次这个结果集的指针就往下移动一次，有数据返回ture，没有数据返回false。
    		当sql执行结果集查询出来后，默认的指针不指向任何数据。只有调用re.next()方法才会指向第一条数据。
            
			*/
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
```



- `String name = rs.getString(xx)`方法把获取任何数据全部变成字符串，它不管数据库底层是什么类型。
- `rs.getXXX('字段名')`,这个字段名不是数据库的字段名，而是查询结果表的字段名。因为有可能在SELECT语句中使用AS起别名了。

- 数据库底层是什么类型，就使用`rs.getXXX()`对应类型的方法，不要乱写

# 五、关于SQL注入

```java
//假设有一个登录功能,其SQL是这样
    
"SELECT username,password FROM user WHERE username='username' AND password='password'";  

//此时进行SQL注入，扭曲SQL的含义。我输入用户名 fasdfa ,密码  fda' OR '1'='1 此时sql就变成

"SELECT username,password FROM user WHERE username='fasdfa' AND password='fda' OR '1'='1 "; 

//因为 '1'='1',这个sql永远是true。即使输入了错误的密码和账号也能登录。

```

- SQL注入最主要的原因是用户输入的信息参与到了SQL语句的编译。

# 六、解决SQL注入

①使用`preparedStatment`预编译的数据库操作对象。

②`ps.executeQuery()`**<font color="red">不用传递sql进去了，他妈的被这个问题搞了一年</font>**

```java
/*
占位符不用加''号，它会根据调用的方法setString()，setInt()判断是否添加''单引号
*/
String sql = "SELECT * FROM users WHERE username=? AND password=?";

/*
使用PreparedStatement对象（名词）的prepareStatement（动词）方法，预编译sql语句，这样用户输入的就不会参与到sql的编译
*/
PreparedStatement ps = conn.prepareStatement(sql);

/*
占位符从1开始，是字符串就用setString(),int类型就用setInt()方法
*/
ps.setString(1, user.getUsername());

ps.setString(2, user.getPassword());

/*
这里不用在把sql当参数传了，会让sql语句重新编译，也会出现SQLException异常。
*/
rs = ps.executeQuery();
```

# 七、Statement和PreparedStatement的比较

|        Statement         |        PreparedStatement         |
| :----------------------: | :------------------------------: |
|     存在SQL注入现象      |          解决了SQL注入           |
| 效率慢，执行一次编译一次 | 效率快，只编译一次。可以多次执行 |
|                          |           支持类型检查           |

- 关于效率问题：

  - 在MySQL中。如果SQL语句与之前编译过的SQL语句一样，那么就不会再次编译这条SQL，而是直接执行。

    使用`PreparedStatement`会预先编译SQL，当再次执行这条SQL的时候，编译器发现这两条SQL都是一样的，不会编译而是直接执行。所以只需要第一次编译。后续多次使用只需要传值就可以了。

  - 使用`statement`因为每次的条件都不同，所以每次执行都需要编译。



- 什么时候使用`statement`
  - 在需要进行SQL注入的时候，也就是需要对象SQL语句进行拼接的时候。比如升序降序
  - 如果只是给SQL语句传递值就用`PreparedStatment`

# 八、JDBC事务机制

①JDBC默认事务自动提交，执行一行提交一行。这是不合理的，应该是执行完成一个功能才全部提交

②关闭JDBC默认的字段提交功能,使用`Connection`对象的方法

```
conn.setAutoCommit(false);//关闭JDBC事务自动提价

conn.commit();//当需要提交事务的时候使用这个代码，一般是一个功能完成后才写

conn.rollback();//出现异常回滚事务，卸载catch语句块中。
```

# 九、行级锁的概念