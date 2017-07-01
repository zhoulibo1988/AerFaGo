package sine.framework.generator.typemapping;

import java.sql.*;

public class DataConnect {
	private final String oracleDriverName = "oracle.jdbc.driver.OracleDriver";

	// 以下使用的Test就是Oracle里的表空间
	private final String oracleUrlToConnect = "jdbc:oracle:thin:@192.168.168.176:1521:orcl";

	/**
	 * getConnection method
	 * 
	 * @return Connection
	 */
	public Connection getConnection() {
		Connection myConnection = null;
		try {
			Class.forName(oracleDriverName);
			myConnection = DriverManager.getConnection(oracleUrlToConnect,
					"jg_credit", "jg_credit");

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return myConnection;

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DataConnect myOracleTest = new DataConnect();
		try {
			Connection myConnection = myOracleTest.getConnection();
			 Statement stmt=null;  
			 ResultSet rs=null;
			System.out.println("Now begin to excute.............");
			String sqlStr = "select cu.* from user_cons_columns cu, user_constraints au " +
					"where cu.constraint_name = au.constraint_name and au.constraint_type = 'P' " +
					"and au.table_name = 'TEST'";
			stmt=myConnection.createStatement();  
			rs=stmt.executeQuery(sqlStr);  
			
			while (rs.next()) {
				//myStringBuffer.append(myResultSet.getInt("area_id") + "  ");
				//rs.getString("COLUMN_NAME");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
