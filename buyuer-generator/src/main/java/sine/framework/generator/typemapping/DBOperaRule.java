package sine.framework.generator.typemapping;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DBOperaRule {
	private static Statement stmt = null;
    private static PreparedStatement pstmt = null;
    private static ResultSet rs = null;
    private static boolean hadErrors = false;
    
    //创建数据库连接
    public static Connection createConnection(){
    	Connection con = null;
    	try{
			Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
			String url = "jdbc:oracle:thin:@localhost:1521:orcl";
			String username = "sjzx";
			String password = "sjzx";
			con  = DriverManager.getConnection(url, username, password);
		}
		catch (Exception e){
			System.out.println(e.getMessage());
		}
    	return con;
    }
    
    //发起事务
	public  static void beginTransaction(){
		Connection conn = createConnection();
		try {
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//执行事务
	public static void commitTransaction() throws SQLException{
		Connection conn = createConnection();
		if(!hadErrors){
			conn.commit();
		}else{
			conn.rollback();
			hadErrors = false ;
		}
		hadErrors = false ;
		conn.setAutoCommit(true);
	}

	//执行插入、删除和更新操作
	 public static void execute(String sql) throws SQLException {
		 Connection conn = createConnection();
		pstmt = conn.prepareStatement(sql);
		if(pstmt != null){
			pstmt.executeUpdate(sql);
		}else{			
			System.out.println("数据库插入数据出错");
		}
	}
	
	//执行查询操作
	 public static ResultSet read(String sql) throws SQLException {
		Connection conn = createConnection();
		pstmt = conn.prepareStatement(sql);
		if(pstmt != null){
			ResultSet tmp = null ;
			tmp = pstmt.executeQuery(sql);
			
			//下面为打印数据表中的字段名称
//			ResultSetMetaData md = tmp.getMetaData();
//			for(int i=1; i<=md.getColumnCount(); i++){ 
//                System.out.println(md.getColumnName(i)); 
//            }
			
			//循环打印数据库中的值
			while(tmp.next()){
				//System.out.println("tmp是否在最后一行：" + tmp.isLast());
				System.out.println("row: " + tmp.getRow());
				System.out.println(tmp.getString(1) + " --> " + tmp.getString(2));
			}
			
			return tmp;
		}else{
			return null;
		}
	}
	
	//执行查询个数操作
	 public static int readCount(String sql) throws SQLException {
		Connection conn = createConnection();
		pstmt = conn.prepareStatement(sql);
		int nCount = 0;
		try{
			if(pstmt != null){
				ResultSet tmp = null;
				tmp = pstmt.executeQuery(sql);
				if(tmp != null && tmp.next()){
					nCount = tmp.getInt(1);
				}else{
					nCount = 0;
				}
			}
		}catch(SQLException e){
			nCount = 0;
		}
		return nCount;
	}
	
	//创建数据库连接
	/*
    public boolean createConnection(){
        try{
        	conn = UtilDbForOracle.getConnection();
            conn.setAutoCommit(false);
        }catch(SQLException e){
            System.out.println("createConnectionError!");
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
	*/
    //进行数据库增，删除，更新操作
	/*
    public boolean executeUpdate(String sql){
        if(conn == null){ 
        	createConnection();
        }
        
        try{
            stmt = con.createStatement();
            int iCount = stmt.executeUpdate(sql);
            System.out.println("操作成功，操作影响的记录数：" + String.valueOf(iCount));
        }catch(SQLException e){
            System.out.println("executeUpdateError!");
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
	*/
	/*
    public boolean executeUpdateBatch(List list){
        if(conn == null) createConnection();
        try{
            stmt = conn.createStatement();
            for(int i = 0; i < list.size(); ++i)
                stmt.addBatch((String) list.get(i));
            int i[] = stmt.executeBatch();
            System.out.println("操作成功，操作影响的记录数：" + i.length);
        }catch(SQLException e){
            System.out.println("executeUpdateBatchError!");
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
	*/
    //进行数据库查询操作
	
    public static ResultSet executeQuery(String sql){
    	Connection conn = createConnection();
    	if(conn == null) createConnection();
        try{
        	
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
        }catch(SQLException e){
            System.out.println("executeQueryError!");
            System.out.println(e.getMessage());
            return null;
        }
        return rs;
    }
	
	
    public static List executeQueryForList(String s) throws SQLException{
        ResultSet rs = executeQuery(s);
        List list = new ArrayList();
        HashMap hash;
        String tmp = null;
        int cols = rs.getMetaData().getColumnCount();
        while(rs.next()){
            hash = new HashMap();
            for(int i = 1; i <= cols; i++){
                tmp = rs.getString(i);
                if(tmp == null)
                    tmp = "";
                hash.put(rs.getMetaData().getColumnName(i).toUpperCase(), tmp);
            }
            list.add(hash);
            hash = null;
        }
        return list;
    }
	
	/*
    public HashMap executeQueryForMap(String s) throws SQLException{
        ResultSet rs = this.executeQuery(s);
        HashMap hash = new HashMap();
        String tmp = null;
        int cols = rs.getMetaData().getColumnCount();
        while(rs.next()){
            hash = new HashMap();
            for(int i = 1; i <= cols; i++){
                tmp = rs.getString(i);
                if(tmp == null)
                    tmp = "";
                hash.put(rs.getMetaData().getColumnName(i).toLowerCase(), tmp);
            }
        }
        return hash;
    }
	*/
	/*
    public ResultSet executeQueryForPage(String sql){
    	if(conn == null) createConnection();
        try{
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery(sql);
        }catch(SQLException e){
            System.out.println("executeQueryError!");
            System.out.println(e.getMessage());
            return null;
        }
        return rs;
    }
	*/
    //对数据库操作进行提交
	/*
    public boolean commit(){
        try{
            conn.commit();
        }catch(SQLException e){
            System.out.println("commitError!");
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
	*/
    //关闭数据库连接
    public void closeDBConnection(){
    	Connection conn = createConnection();
        if(conn != null){
            try{
//                stmt.close();
                pstmt.close();
                conn.close();
            }catch(SQLException e){
                System.out.println("closeDBConnectionError!");
                e.printStackTrace();
            }finally{
                try{
//                    stmt.close();
                    pstmt.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
                conn = null;
            }
        }
    }
    public static String getPrimaryKeyStr(){
    	String pkStr = "";
    	try{
    		DBOperaRule db = new DBOperaRule();
        	String sqlStr = "select cu.* from user_cons_columns cu, user_constraints au " +
    				"where cu.constraint_name = au.constraint_name and au.constraint_type = 'P' " +
    				"and au.table_name = 'TEST'";
        	List list = db.executeQueryForList(sqlStr);
        	for(int i=0;i<list.size();i++){
        		HashMap map = (HashMap)list.get(i);
        		pkStr = map.get("COLUMN_NAME").toString();
        		//System.out.println("主键："+map.get("COLUMN_NAME"));
        	}
    	}catch (Exception e) {
			e.printStackTrace();
		}
    	return pkStr;
    }
    public static void main(String args[]){
    	try{
        	String sqlStr = "select cu.* from user_cons_columns cu, user_constraints au " +
    				"where cu.constraint_name = au.constraint_name and au.constraint_type = 'P' " +
    				"and au.table_name = 'TEST'";
        	List list = DBOperaRule.executeQueryForList(sqlStr);
        	for(int i=0;i<list.size();i++){
        		HashMap map = (HashMap)list.get(i);
        		System.out.println("主键："+map.get("COLUMN_NAME"));
        	}
    	}catch (Exception e) {
			e.printStackTrace();
		}
    }
}
