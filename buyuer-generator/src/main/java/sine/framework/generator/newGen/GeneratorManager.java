package sine.framework.generator.newGen;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sine.framework.generator.util.GeneratorException;

/**
 * 自动生成 前期准备 对外方法
 * 
 * @author Administrator
 * 
 */
public class GeneratorManager {
    public GeneratorCore generatorCore = new GeneratorCore();

    public static GeneratorPrintUtils PrintUtils = new GeneratorPrintUtils();

    public GeneratorManager() {
        generatorCore.setOutRootDir(GeneratorProperties.getProperty("outRoot"));
    }

    /**
     * 打印所有数据库内的表名
     * 
     * @throws Exception
     */
    public static void printAllTableNames() throws Exception {
        List<TableInfo> tableList = TableFactory.getTableFactory().getAllTables();
        PrintUtils.printAllTableNames(tableList);
    }

    /**
     * 生成文件 tableName 为null或者* 时候 为全表操作
     * 
     * @param templateRootDir
     *            模版目录
     * @throws Exception
     */
    public void generateByTables(String tableName, String templateRootDir) throws Exception {
        processByAllTable(tableName, templateRootDir, false);
    }

    /**
     * 删除文件 tableName 为null或者* 时候 为全表操作
     * 
     * @param templateRootDir
     *            模版目录
     * @throws Exception
     */
    public void deltetByTables(String tableName, String templateRootDir) throws Exception {
        processByAllTable(tableName, templateRootDir, true);
    }

    /**
     * 根据所有表生成文件 准备阶段
     * 
     * @param templateRootDir
     * @param isDelete
     * @throws Exception
     */
    public void processByAllTable(String tableName, String templateRootDir, boolean isDelete) throws Exception {
        // 获取 模版完整路径
        getGeneratorCore(templateRootDir);
        // 获取表集合
        List<TableInfo> tableList = new ArrayList<TableInfo>();
        if (tableName == null || ("*").equals(tableName)) {
            tableList = TableFactory.getTableFactory().getAllTables();
        } else {
            TableInfo table = TableFactory.getTableFactory().getTable(tableName);
            tableList.add(table);
        }
        // 异常收集器
        List<Exception> exceptions = new ArrayList<Exception>();
        for (TableInfo table : tableList) {
            try {
                processByTable(generatorCore, table, isDelete);
            } catch (GeneratorException ge) {
                exceptions.addAll(ge.getExceptions());
            }
        }
        // 生成一些额外的

    }

    /**
     * 根据表名生成文件
     * 
     * @param generatorCore
     * @param table
     * @param isDelete
     * @throws Exception
     */
    private void processByTable(GeneratorCore generatorCore, TableInfo table, boolean isDelete) throws Exception {
        // TODO Auto-generated method stub
        // 对表的信息进行反向获取，并且添加 配置文件的信息
        GeneratorModel m = GeneratorModelUtils.newFromTable(table);
        PrintUtils.printBeginProcess(table.getTableName() + " => " + table.getClassName(), isDelete);
        if (isDelete)
            generatorCore.deleteBy(m.templateModel, m.filePathModel);
        else
            generatorCore.generateBy(m.templateModel, m.filePathModel);
    }

    /**
     * 设置TemplateRootDir
     * 
     * @param templateRootDir
     * @return
     */
    private GeneratorCore getGeneratorCore(String templateRootDir) {
        generatorCore.setTemplateRootDir(new File(templateRootDir).getAbsoluteFile());
        return generatorCore;
    }

    /** 生成器的上下文，存放的变量将可以在模板中引用 */
    public static class GeneratorContext {
        static ThreadLocal<Map> context = new ThreadLocal<Map>();

        public static void clear() {
            Map m = context.get();
            if (m != null)
                m.clear();
        }

        public static Map getContext() {
            Map map = context.get();
            if (map == null) {
                setContext(new HashMap());
            }
            return context.get();
        }

        public static void setContext(Map map) {
            context.set(map);
        }

        public static void put(String key, Object value) {
            getContext().put(key, value);
        }
    }
}
