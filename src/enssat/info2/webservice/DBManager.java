package enssat.info2.webservice;

import java.sql.*;
import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;


public class DBManager {

	private static DBManager instance;

	private ResourceBundle properties;

	private Driver driver;

	private static String resourceBundle = "config";
	private Connection connection;

	private DBManager() {
		properties = ResourceBundle.getBundle(resourceBundle);
		try {
			Class.forName(properties.getString("DB_DRIVER"));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static DBManager getInstance() {
		if (instance == null) {
			synchronized (DBManager.class) {
				instance = new DBManager();
			}
		}
		return instance;
	}

	public Connection getConnection() {

		try {
			if(connection!=null && connection.isClosed()){
				System.err.println("Connection Closed ===================== IMPORTANT");
			}
			if(connection==null || connection.isClosed()){
				connection = DriverManager.getConnection(properties.getString("JDBC_URL"), properties.getString("DB_LOGIN"),
						properties.getString("DB_PASSWORD"));
				//connection to the right database
				String query = "USE sql7283170;";
				Statement sta = connection.createStatement();
				sta.executeQuery(query);
			}
		} catch (SQLException sqle) { sqle.printStackTrace(); }
		return connection;

	}

	public void cleanup(Connection connection, Statement stat, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (stat != null) {
			try {
				stat.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * permet de tester la connexion à la DB
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Connection c = DBManager.getInstance().getConnection();
		if (c != null) {
			try {
				System.out.println("Connection to db : " + c.getCatalog());
				Properties p = c.getClientInfo();
				Enumeration<Object> keys = p.keys();
				while (keys.hasMoreElements()) {
					String key = (String) keys.nextElement();
					System.out.println(key + ":" + p.getProperty(key));
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				DBManager.getInstance().cleanup(c, null, null);
			}
		}
	}
}
