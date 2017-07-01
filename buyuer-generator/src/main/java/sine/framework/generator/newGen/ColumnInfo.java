package sine.framework.generator.newGen;

import sine.framework.generator.typemapping.DatabaseDataTypesUtils;
import sine.framework.generator.util.StringHelper;

public class ColumnInfo implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8000854269637424702L;

	private String tableName;
	private String columnName;
	private int dataType;
	private String javaType;
	private String typeName;
	private int columnSize;
	private int decimalDigits;
	private boolean nullAble;
	private String remarks;
	private String defValue;
	private boolean isPK;
	
	
	public ColumnInfo() {
		// TODO Auto-generated constructor stub
	}

	public ColumnInfo(String tableName, String columnName, int dataType,
			String typeName, int columnSize, int decimalDigits,
			boolean nullAble, String remarks, String defValue, boolean isPK) {
		super();
		this.tableName = tableName;
		this.columnName = columnName;
		this.dataType = dataType;
		this.typeName = typeName;
		this.columnSize = columnSize;
		this.decimalDigits = decimalDigits;
		this.nullAble = nullAble;
		this.remarks = remarks==null?"NULL":remarks;
		this.defValue = defValue;
		this.isPK = isPK;
		getJdbcTypeName();
		this.javaType = getJavaType();
	}
	
	public String getJavaName(){
		return StringHelper.makeAllWordFirstLetterUpperCase(StringHelper.toUnderscoreName(getColumnName()));
	}
	
	public String getJavaType(){
		return DatabaseDataTypesUtils.getPreferredJavaType(dataType, columnSize, decimalDigits);
	}
	
	/** 
     * 第一个字母小写的columName,等价于: StringHelper.uncapitalize(getColumnName()),示例值: birthDate
     **/
	public String getColumnNameFirstLower() {
		return StringHelper.uncapitalize(getJavaName());
	}

    /** 
     * 全部小写的columName,等价于: getColumnName().toLowerCase(),示例值: birthdate
     **/
	public String getColumnNameLower() {
		return getJavaName().toLowerCase();
	}
	
	/** 列是否是String类型 */
	public boolean getIsStringColumn() {
		return DatabaseDataTypesUtils.isString(getJavaType());
	}
	
	/** 列是否是日期类型 */
	public boolean getIsDateTimeColumn() {
		return DatabaseDataTypesUtils.isDate(getJavaType());
	}
	
	/** 列是否是Number类型 */
	public boolean getIsNumberColumn() {
		return DatabaseDataTypesUtils.isFloatNumber(getJavaType()) 
			|| DatabaseDataTypesUtils.isIntegerNumber(getJavaType());
	}
	
	/** 列是否是Long类型 */
	public boolean getIsLongColumn() {
		return DatabaseDataTypesUtils.isLongNumber(getJavaType());
	}
	
	/** 列是否是 字典内部 类型 */
	public boolean getIsDTColumn() {
		if(columnSize==10){
			return true;
		}else{
			return false;
		}
	}
	
	/** 检查是否包含某些关键字,关键字以逗号分隔 */
	public boolean contains(String keywords) {
		if(keywords == null) throw new IllegalArgumentException("'keywords' must be not null");
		return StringHelper.contains(getJavaName(), keywords.split(","));
	}
	
	/*===============================================================**///
	
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public int getDataType() {
		return dataType;
	}

	/**
	 * 同时获取 jdbcTypeName
	 * @param dataType
	 */
	public void setDataType(int dataType) {
		this.dataType = dataType;
		getJdbcTypeName();
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public int getColumnSize() {
		return columnSize;
	}

	public void setColumnSize(int columnSize) {
		this.columnSize = columnSize;
	}

	public int getDecimalDigits() {
		return decimalDigits;
	}

	public void setDecimalDigits(int decimalDigits) {
		this.decimalDigits = decimalDigits;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getDefValue() {
		return defValue;
	}

	public void setDefValue(String defValue) {
		this.defValue = defValue;
	}

	public boolean isNullAble() {
		return nullAble;
	}

	public void setNullAble(boolean nullAble) {
		this.nullAble = nullAble;
	}

	public boolean getIsPK() {
		return isPK;
	}

	public void setPK(boolean isPK) {
		this.isPK = isPK;
	}
	
	public String getJdbcTypeName() {
		switch (this.dataType) {
		case -5:
			return "BIGINT BigInt";
		case -2:
			return "BINARY";
		case -7:
			return "BIT";
		case 1:
			return "CHAR";
		case 91:
//			return "DATE";
			return "TIMESTAMP";
		case 3:
			return "DECIMAL";
		case 8:
			return "DOUBLE";
		case 6:
			return "FLOAT";
		case 4:
			return "INTEGER";
		case -4:
			return "LONGVARBINARY";
		case -1:
			return "LONGVARCHAR";
		case 0:
			return "NULL";
		case 2:
			return "NUMERIC";
		case 1111:
			return "OTHER";
		case 7:
			return "REAL";
		case 5:
			return "SMALLINT";
		case 92:
			return "TIME";
		case 93:
			return "TIMESTAMP";
		case -6:
			return "TINYINT";
		case -3:
			return "VARBINARY";
		case 12:
			return "VARCHAR";
		}
		return "UNKNOW";
	}

	public String toString() {
		return String.format(
				"%1$-20s %2$-18s %3$-8s %4$-10s %5$-1s",
				new Object[] {
						this.columnName,
						String.format(
								"%1$s(%2$s"
										+ ((this.decimalDigits == 0) ? ""
												: ",%3$s") + ")",
								new Object[] { this.javaType,
										Integer.valueOf(this.columnSize),
										Integer.valueOf(this.decimalDigits) }),
						(this.nullAble) ? "" : "NOTNULL",
						(this.defValue != null) ? this.defValue : "",
						(this.remarks != null) ? "//" + this.remarks : "" });
	}

}
