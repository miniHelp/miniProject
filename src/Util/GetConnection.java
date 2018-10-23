package Util;
import java.sql.Connection;
import java.sql.DriverManager;


//先建立連線
public class GetConnection {

	   public GetConnection() {  //constructor
	        this.getConnection();
	    }

	    public Connection getConnection() {
	    	Connection conn = null;   
	        try {
	            Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();   //Driver name
	            String url = "jdbc:oracle:thin:@192.168.0.23:1521:zvo11g01"; //(IP : Port : SID)   
	            String user = "mypaycenter";
	            String password = "myPay4Zv";
	            conn = DriverManager.getConnection(url, user, password);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return conn;
	    }
	    
	    public Connection getMypayConnection() {
	    	Connection conn = null;   
	        try {
	            Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();   //Driver name
	            String url = "jdbc:oracle:thin:@192.168.0.23:1521:zvo11g01"; //(IP : Port : SID)   
	            String user = "mypay";
	            String password = "myPay4Zv";
	            conn = DriverManager.getConnection(url, user, password);
	            DriverManager.setLoginTimeout(5);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return conn;
	    }
}
