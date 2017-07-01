package sine.framework.generator.newGen;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import sine.framework.generator.util.GLogger;

public class DbHelper {
	public static final String PKTABLE_NAME = "PKTABLE_NAME";
	public static final String PKCOLUMN_NAME = "PKCOLUMN_NAME";
	public static final String FKTABLE_NAME = "FKTABLE_NAME";
	public static final String FKCOLUMN_NAME = "FKCOLUMN_NAME";
	public static final String KEY_SEQ = "KEY_SEQ";

	public void close(ResultSet rs, PreparedStatement ps,
			Statement... statements) {
		try {
			if (ps != null)
				ps.close();
			if (rs != null)
				rs.close();
			for (Statement s : statements) {
				s.close();
			}
		} catch (Exception e) {
		}
	}

	public boolean isOracleDataBase(Connection conn) {
		try {
			boolean ret = false;
			ret = (conn.getMetaData().getDatabaseProductName().toLowerCase()
					.indexOf("oracle") != -1);
			return ret;
		} catch (SQLException s) {
			return false;
			// throw new RuntimeException(s);
		}
	}

	public String queryForString(Connection conn, String sql) {
		Statement s = null;
		ResultSet rs = null;
		try {
			s = conn.createStatement();
			rs = s.executeQuery(sql);
			if (rs.next()) {
				return rs.getString(1);
			}
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			close(rs, null, s);
		}
	}

	public String getDatabaseStructureInfo(Connection conn, String schema,
			String catalog) {
		DatabaseMetaData metaData = null;
		try {
			metaData = conn.getMetaData();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultSet schemaRs = null;
		ResultSet catalogRs = null;
		String nl = System.getProperty("line.separator");
		StringBuffer sb = new StringBuffer(nl);
		// Let's give the user some feedback. The exception
		// is probably related to incorrect schema configuration.
		sb.append("Configured schema:").append(schema).append(nl);
		sb.append("Configured catalog:").append(catalog).append(nl);

		try {
			schemaRs = metaData.getSchemas();
			sb.append("Available schemas:").append(nl);
			while (schemaRs.next()) {
				sb.append("  ").append(schemaRs.getString("TABLE_SCHEM"))
						.append(nl);
			}
		} catch (SQLException e2) {
			GLogger.warn("Couldn't get schemas", e2);
			sb.append("  ?? Couldn't get schemas ??").append(nl);
		} finally {
			close(schemaRs, null);
		}

		try {
			catalogRs = metaData.getCatalogs();
			sb.append("Available catalogs:").append(nl);
			while (catalogRs.next()) {
				sb.append("  ").append(catalogRs.getString("TABLE_CAT"))
						.append(nl);
			}
		} catch (SQLException e2) {
			GLogger.warn("Couldn't get catalogs", e2);
			sb.append("  ?? Couldn't get catalogs ??").append(nl);
		} finally {
			close(catalogRs, null);
		}
		return sb.toString();
	}

	/**
	 * 获取 Oracle表的 comments
	 * 
	 * @param conn
	 * @param table
	 * @return
	 */
	public String getOracleTableComments(Connection conn, String table) {
		String sql = "SELECT comments FROM user_tab_comments WHERE table_name='"
				+ table + "'";
		return queryForString(conn, sql);
	}

	/**
	 * 获取 Oracle表字段的 comments
	 * 
	 * @param conn
	 * @param table
	 * @param column
	 * @return
	 */
	public String getOracleColumnComments(Connection conn, String table,
			String column) {
		String sql = "SELECT comments FROM user_col_comments WHERE table_name='"
				+ table + "' AND column_name = '" + column + "'";
		return queryForString(conn, sql);
	}

	/**
	 * This method was created in VisualAge.
	 */
	public void initImportedKeys(DatabaseMetaData dbmd, String catalog,
			String schema, TableInfo table) throws java.sql.SQLException {

		String tableName = table.getTableName();
		// get imported keys a
		ResultSet fkeys = dbmd.getImportedKeys(catalog, schema, tableName);
		while (fkeys.next()) {
			String pktable = fkeys.getString(PKTABLE_NAME);
			String pkcol = fkeys.getString(PKCOLUMN_NAME);
			String fktable = fkeys.getString(FKTABLE_NAME);
			String fkcol = fkeys.getString(FKCOLUMN_NAME);
			String seq = fkeys.getString(KEY_SEQ);
			Integer iseq = new Integer(seq);
			ForeignKeys importedKeys = new ForeignKeys(table);
			table.setImportedKeys(importedKeys);
			importedKeys.addForeignKey(pktable, pkcol, fkcol, iseq);
		}
		fkeys.close();
	}

	/**
	 * This method was created in VisualAge.
	 */
	public void initExportedKeys(DatabaseMetaData dbmd, String catalog,
			String schema, TableInfo table) throws java.sql.SQLException {

		String tableName = table.getTableName();
		// get imported keys a
		ResultSet fkeys = dbmd.getExportedKeys(catalog, schema, tableName);
		while (fkeys.next()) {
			String pktable = fkeys.getString(PKTABLE_NAME);
			String pkcol = fkeys.getString(PKCOLUMN_NAME);
			String fktable = fkeys.getString(FKTABLE_NAME);
			String fkcol = fkeys.getString(FKCOLUMN_NAME);
			String seq = fkeys.getString(KEY_SEQ);
			Integer iseq = new Integer(seq);
			ForeignKeys exportedKeys = new ForeignKeys(table);
			table.setExportedKeys(exportedKeys);
			exportedKeys.addForeignKey(fktable, fkcol, pkcol, iseq);
		}
		fkeys.close();
	}
}
