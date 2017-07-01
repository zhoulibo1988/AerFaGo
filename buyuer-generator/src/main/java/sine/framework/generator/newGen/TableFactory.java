package sine.framework.generator.newGen;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import sine.framework.generator.util.GLogger;
import sine.framework.generator.util.StringHelper;


/**
 * 
 * 根据数据库表的元数据(metadata)创建Table对象
 * 
 * <pre>
 * getTable(sqlName) : 根据数据库表名,得到table对象
 * getAllTable() : 搜索数据库的所有表,并得到table对象列表
 * </pre>
 * @author badqiu
 * @email badqiu(a)gmail.com
 */

/**
 * 对于tablefactory 的定义 是 获取 表 以及 字段信息
 * 
 * @author Administrator
 * 
 */
public class TableFactory {
	private static String catalog = null;
	private static String schema = null;
	private static Connection conn = null;
	private static DatabaseMetaData metaData = null;
	private static TableFactory tableFactory = null;
	private DbHelper dbHelper = new DbHelper();

	public TableFactory() {
	}

	/**
	 * 初始化 tablefactory
	 * 
	 * @return
	 */
	public synchronized static TableFactory getTableFactory() {
		if (tableFactory == null)
			tableFactory = new TableFactory();

		String _catalog = GeneratorProperties.getNullIfBlank("jdbc.catalog");
		catalog = (_catalog == null ? null : _catalog.toUpperCase());
		String _schema = GeneratorProperties.getNullIfBlank("jdbc.schema");
		schema = (_schema == null ? null : _schema.toUpperCase());
		try {
			conn = DataSourceProvider.getConnection();
			metaData = conn.getMetaData();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tableFactory;
	}

	public TableInfo getTable(String tableName) {
		return getTable(catalog, schema, tableName);
	}

	private TableInfo getTable(String catalog, String schema, String tableName) {
		TableInfo t = null;
		try {
			t = getTableNext(catalog, schema, tableName);
			if (t == null && !tableName.equals(tableName.toUpperCase())) {
				t = getTableNext(catalog, schema, tableName.toUpperCase());
			}
			if (t == null && !tableName.equals(tableName.toLowerCase())) {
				t = getTableNext(catalog, schema, tableName.toLowerCase());
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		if (t == null) {
			throw new NotFoundTableException(
					"not found table with give name:"
							+ tableName
							+ (dbHelper.isOracleDataBase(conn) ? " \n databaseStructureInfo:"
									+ dbHelper.getDatabaseStructureInfo(conn,
											schema, catalog)
									: ""));
		}
		return t;
	}

	private TableInfo getTableNext(String catalog, String schema,
			String tableName) throws SQLException {
		if (tableName == null || tableName.trim().length() == 0)
			throw new IllegalArgumentException("tableName must be not empty");
		ResultSet rs = metaData.getTables(catalog, schema, tableName, null);
		while (rs.next()) {
			TableInfo tableInfo = createTable(conn, rs);
			return tableInfo;
		}
		return null;
	}

	/**
	 * 获得所有表
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<TableInfo> getAllTables() throws SQLException {
		ResultSet rs = metaData.getTables(catalog, schema, null, null);
		List<TableInfo> tables = new ArrayList<TableInfo>();
		while (rs.next()) {
			if(!rs.getString("TABLE_NAME").toUpperCase().contains("SYS"))
				tables.add(createTable(conn, rs));
		}
		return tables;
	}

	/**
	 * 获得表信息
	 * 
	 * @param tableName
	 * @return
	 */
	public TableInfo getTableInfo(String tableName) {
		return getTableInfo(catalog, schema, tableName.toUpperCase());
	}

	private TableInfo getTableInfo(String catalog, String schema,
			String tableName) {
		TableInfo t = null;
		try {
			if (tableName == null || tableName.trim().length() == 0)
				throw new IllegalArgumentException(
						"tableName must be not empty");
			// catalog = StringHelper.defaultIfEmpty(catalog, null);
			// schema = StringHelper.defaultIfEmpty(schema, null);
			// Connection conn = getConnection();
			DatabaseMetaData dbMetaData = conn.getMetaData();
			ResultSet rs = dbMetaData.getTables(catalog, schema, tableName,
					null);
			while (rs.next()) {
				TableInfo table = createTable(conn, rs);
				return table;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		if (t == null) {
			throw new NotFoundTableException(
					"not found table with give name:"
							+ tableName
							+ (dbHelper.isOracleDataBase(conn) ? " \n databaseStructureInfo:"
									+ dbHelper.getDatabaseStructureInfo(conn,
											schema, catalog)
									: ""));
		}
		return t;
	}

	private TableInfo createTable(Connection conn, ResultSet rs)
			throws SQLException {
		String tableName = null;
		try {
			String schemaName = rs.getString("TABLE_SCHEM") == null ? "" : rs
					.getString("TABLE_SCHEM");
			tableName = rs.getString("TABLE_NAME");
			String tableType = rs.getString("TABLE_TYPE");
			String remarks = rs.getString("REMARKS");
			// 判断是否是 oracle 数据库
			if (remarks == null && dbHelper.isOracleDataBase(conn)) {
				remarks = dbHelper.getOracleTableComments(conn, tableName);
			}

			// 设置 tableinfo
			TableInfo table = new TableInfo();
			table.setTableName(tableName);
			table.setTableAlias(removeTableSqlNamePrefix(tableName));
			table.setRemarks(remarks);
			table.setSchema(schemaName);
			table.setClassName(getClassName(tableName));
			table.setTableType(tableType);
			retriveTableColumns(table);

			dbHelper.initExportedKeys(conn.getMetaData(), catalog, schema,
					table);
			dbHelper.initImportedKeys(conn.getMetaData(), catalog, schema,
					table);
			return table;
		} catch (SQLException e) {
			throw new RuntimeException("create table object error,tableName:"
					+ tableName, e);
		}
	}

	/**
	 * 添加 主键信息 以及 字段信息
	 * 
	 * @param table
	 * @throws SQLException
	 */
	private void retriveTableColumns(TableInfo table) throws SQLException {
		GLogger.trace("-------setColumns(" + table.getTableName() + ")");

		List<String> primaryKeys = getTablePrimaryKeys(table);
		table.setPrimaryKeyColumns(primaryKeys);

		List<ColumnInfo> columnsList = getTableColumns(table, primaryKeys);
		table.setColumnsList(columnsList);

		// In case none of the columns were primary keys, issue a warning.
		if (primaryKeys.size() == 0) {
			GLogger.warn("WARNING: The JDBC driver didn't report any primary key columns in "
					+ table.getTableName());
		}
	}

	private List<String> getTablePrimaryKeys(TableInfo table)
			throws SQLException {
		// get the primary keys
		List<String> primaryKeys = new LinkedList<String>();
		ResultSet primaryKeyRs = null;
		primaryKeyRs = metaData.getPrimaryKeys(catalog, schema,
				table.getTableName());
		while (primaryKeyRs.next()) {
			String columnName = primaryKeyRs.getString("COLUMN_NAME");
			String primaryKeyStrZdy = "";
			GLogger.trace("primary key:" + columnName);
			String pkS[] = columnName.toLowerCase().split("_");
			String endStrs = "";
			if(pkS.length>0){
				for(int i=1;i<pkS.length;i++){
					String endStr  = pkS[i].substring(0,1).toUpperCase()+pkS[i].substring(1,pkS[i].length());
					endStrs +=endStr;
				}
				primaryKeyStrZdy = pkS[0]+endStrs;				
			}
			columnName = primaryKeyStrZdy;
			System.out.println("主键是："+columnName);
			primaryKeys.add(columnName);
		}
		primaryKeyRs.close();
		return primaryKeys;
	}

	/**
	 * 获取表字段信息
	 * 
	 * @param table
	 * @param primaryKeys
	 * @return
	 * @throws SQLException
	 */
	private List<ColumnInfo> getTableColumns(TableInfo table,
			List<String> primaryKeys) throws SQLException {
		// get the columns
		List<ColumnInfo> columns = new LinkedList<ColumnInfo>();
		ResultSet columnRs = metaData.getColumns(getCatalog(), getSchema(),
				table.getTableName(), null);

		while (columnRs.next()) {
			String tableName = columnRs.getString("TABLE_NAME");
			String columnName = columnRs.getString("COLUMN_NAME");
			int dataType = columnRs.getInt("DATA_TYPE");
			String typeName = columnRs.getString("TYPE_NAME");
			int columnSize = columnRs.getInt("COLUMN_SIZE");
			int decimalDigits = columnRs.getInt("DECIMAL_DIGITS");
			// if columnNoNulls or columnNullableUnknown assume "not nullable"
			boolean nullAble = (DatabaseMetaData.columnNullable == columnRs
					.getInt("NULLABLE"));
			String remarks = columnRs.getString("REMARKS");
			String defValue = columnRs.getString("COLUMN_DEF");
			boolean isPk = primaryKeys.contains(columnName);
			if (remarks == null && dbHelper.isOracleDataBase(conn)) {
				remarks = dbHelper.getOracleColumnComments(conn,
						table.getTableName(), columnName);
			}
			ColumnInfo columnInfo = new ColumnInfo(tableName, columnName,
					dataType, typeName, columnSize, decimalDigits, nullAble,
					remarks, defValue, isPk);
			columns.add(columnInfo);
		}
		columnRs.close();
		return columns;
	}

	/**
	 * 根据tableName 生成className  sys_user  to sysUser
	 * @param tableName
	 * @return
	 */
	public String getClassName(String tableName) {
		String removedPrefixSqlName = removeTableSqlNamePrefix(tableName);
		return StringHelper.makeAllWordFirstLetterUpperCase(StringHelper
				.toUnderscoreName(removedPrefixSqlName));
	}

	public static String removeTableSqlNamePrefix(String tableName) {
		String prefixs = GeneratorProperties.getProperty("tableRemovePrefixes","");
		for (String prefix : prefixs.split(",")) {
			String removedPrefixSqlName = StringHelper.removePrefix(tableName,
					prefix, true);
			if (!removedPrefixSqlName.equals(tableName)) {
				return removedPrefixSqlName;
			}
		}
		return tableName;
	}

	public static void setTableFactory(TableFactory tableFactory) {
		TableFactory.tableFactory = tableFactory;
	}

	public static String getCatalog() {
		return catalog;
	}

	public static void setCatalog(String catalog) {
		TableFactory.catalog = catalog;
	}

	public static String getSchema() {
		return schema;
	}

	public static void setSchema(String schema) {
		TableFactory.schema = schema;
	}
	
	public static class NotFoundTableException extends RuntimeException {
		private static final long serialVersionUID = 5976869128012158628L;
		public NotFoundTableException(String message) {
			super(message);
		}
	}

}
