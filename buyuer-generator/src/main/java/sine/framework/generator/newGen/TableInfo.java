package sine.framework.generator.newGen;

import java.util.ArrayList;
import java.util.List;

import sine.framework.generator.util.StringHelper;

public class TableInfo {
    private String tableName;

    private String className;

    private String remarks;

    private String catalog;

    private String schema;

    private String tableType;

    private String primaryKeyStr;

    private List<ColumnInfo> columnsList = new ArrayList<ColumnInfo>();

    // private ColumnInfo primaryKey;
    private List<String> primaryKeyColumns = new ArrayList<String>();

    // private List<ColumnInfo> foreignKeyColumns = new ArrayList<ColumnInfo>();

    private String tableAlias;

    private ForeignKeys exportedKeys;

    private ForeignKeys importedKeys;

    public String getPrimaryKeyStr() {
        if (primaryKeyColumns.size() == 1) {
            primaryKeyStr = primaryKeyColumns.get(0).toString();
        }
        return primaryKeyStr;
    }

    public void setPrimaryKeyStr(String primaryKeyStr) {
        this.primaryKeyStr = primaryKeyStr;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getCatalog() {
        return catalog;
    }

    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public List<ColumnInfo> getColumnsList() {
        return columnsList;
    }

    public void setColumnsList(List<ColumnInfo> columnsList) {
        this.columnsList = columnsList;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public List<String> getPrimaryKeyColumns() {
        return primaryKeyColumns;
    }

    public void setPrimaryKeyColumns(List<String> primaryKeyColumns) {
        this.primaryKeyColumns = primaryKeyColumns;
    }

    public String getTableAlias() {
        return tableAlias;
    }

    public void setTableAlias(String tableAlias) {
        this.tableAlias = tableAlias;
    }

    public ForeignKeys getExportedKeys() {
        return exportedKeys;
    }

    public void setExportedKeys(ForeignKeys exportedKeys) {
        this.exportedKeys = exportedKeys;
    }

    public ForeignKeys getImportedKeys() {
        return importedKeys;
    }

    public void setImportedKeys(ForeignKeys importedKeys) {
        this.importedKeys = importedKeys;
    }

    public String getClassNameLower() {
        return className.toLowerCase();
    }

    /**
     * 第一个字母小写的columName,等价于: StringHelper.uncapitalize(getColumnName()),示例值: birthDate
     **/
    public String getClassNameFirstLower() {
        return StringHelper.uncapitalize(getClassName());
    }

    public String toString() {
        StringBuilder sbd = new StringBuilder();
        sbd.append(String.format("%1$-30s %2$-1s", new Object[] { String.format("%1$s:%2$s.%3$s", new Object[] { this.tableType, this.schema, this.tableName }), String.format("%1$-1s", new Object[] { (this.remarks != null) ? this.remarks : "" }) }));

        sbd.append("\n--------------------------------------------------------\n");
        for (ColumnInfo col : this.columnsList) {
            sbd.append(col.toString());
            sbd.append("\n");
        }
        sbd.deleteCharAt(sbd.length() - 1);
        return sbd.toString();
    }

}
