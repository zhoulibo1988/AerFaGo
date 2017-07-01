package sine.framework.generator.newGen;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import sine.framework.generator.util.BeanHelper;
import sine.framework.generator.util.FileHelper;
import sine.framework.generator.util.FreemarkerHelper;
import sine.framework.generator.util.GLogger;
import sine.framework.generator.util.GeneratorException;
import sine.framework.generator.util.IOHelper;
import sine.framework.generator.util.StringHelper;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 代码生成器核心引擎
 * 
 * 主要提供以下两个方法供外部使用
 * 
 * <pre>
 * generateBy() 用于生成文件
 * deleteBy() 用于删除生成的文件
 * </pre>
 * 
 * @author buyuer
 * @email ayyocean@163.com
 */
public class GeneratorCore {
    private static final String GENERATOR_INSERT_LOCATION = "generator-insert-location";

    private List templateRootDirs = new ArrayList();

    private String outRootDir;

    private boolean ignoreTemplateGenerateException = true;

    private String removeExtensions = System.getProperty("generator.removeExtensions", ".ftl,.vm");

    private boolean isCopyBinaryFile = true;

    // 需要处理的模板，使用逗号分隔符,示例值: java_src/**,java_test/**
    private String includes = System.getProperty("generator.includes");

    // 不需要处理的模板，使用逗号分隔符,示例值: java_src/**,java_test/**
    private String excludes = System.getProperty("generator.excludes");

    private String sourceEncoding = System.getProperty("generator.sourceEncoding", "UTF-8");

    private String outputEncoding = System.getProperty("generator.outputEncoding", "UTF-8");

    public GeneratorCore() {
    }

    public void setTemplateRootDir(File templateRootDir) {
        setTemplateRootDirs(new File[] { templateRootDir });
    }

    public void setTemplateRootDirs(File... templateRootDirs) {
        this.templateRootDirs = Arrays.asList(templateRootDirs);
    }

    @SuppressWarnings("unchecked")
    public void addTemplateRootDir(File f) {
        templateRootDirs.add(f);
    }

    public boolean isIgnoreTemplateGenerateException() {
        return ignoreTemplateGenerateException;
    }

    public void setIgnoreTemplateGenerateException(boolean ignoreTemplateGenerateException) {
        this.ignoreTemplateGenerateException = ignoreTemplateGenerateException;
    }

    public boolean isCopyBinaryFile() {
        return isCopyBinaryFile;
    }

    public void setCopyBinaryFile(boolean isCopyBinaryFile) {
        this.isCopyBinaryFile = isCopyBinaryFile;
    }

    public String getSourceEncoding() {
        return sourceEncoding;
    }

    public void setSourceEncoding(String sourceEncoding) {
        if (sourceEncoding == null)
            throw new IllegalArgumentException("sourceEncoding must be not null");
        this.sourceEncoding = sourceEncoding;
    }

    public String getOutputEncoding() {
        return outputEncoding;
    }

    public void setOutputEncoding(String outputEncoding) {
        if (outputEncoding == null)
            throw new IllegalArgumentException("outputEncoding must be not null");
        this.outputEncoding = outputEncoding;
    }

    /**
     * 设置需要处理的模板，使用逗号分隔符,示例值: java_src/**,java_test/**
     * 
     * @param includes
     */
    public void setIncludes(String includes) {
        this.includes = includes;
    }

    /** 设置不处理的模板路径,可以使用ant类似的值,使用逗号分隔，示例值： **\*.ignore */
    public void setExcludes(String excludes) {
        this.excludes = excludes;
    }

    public void setOutRootDir(String v) {
        if (v == null)
            throw new IllegalArgumentException("outRootDir must be not null");
        this.outRootDir = v;
    }

    public String getOutRootDir() {
        return outRootDir;
    }

    public void setRemoveExtensions(String removeExtensions) {
        this.removeExtensions = removeExtensions;
    }

    public void deleteOutRootDir() throws IOException {
        if (StringHelper.isBlank(getOutRootDir()))
            throw new IllegalStateException("'outRootDir' property must be not null.");
        GLogger.println("[delete dir]    " + getOutRootDir());
        FileHelper.deleteDirectory(new File(getOutRootDir()));
    }

    /**
     * 生成文件
     * 
     * @param templateModel
     *            生成器模板可以引用的变量
     * @param filePathModel
     *            文件路径可以引用的变量
     * @throws Exception
     */
    public GeneratorCore generateBy(Map templateModel, Map filePathModel) throws Exception {
        processTemplateRootDirs(templateModel, filePathModel, false);
        return this;
    }

    /**
     * 删除生成的文件
     * 
     * @param templateModel
     *            生成器模板可以引用的变量
     * @param filePathModel
     *            文件路径可以引用的变量
     * @return
     * @throws Exception
     */
    public GeneratorCore deleteBy(Map templateModel, Map filePathModel) throws Exception {
        processTemplateRootDirs(templateModel, filePathModel, true);
        return this;
    }

    /**
     * 检查 outRootDir，templateRootDirs 为空抛出异常 同时对templateRootDirs下的文件进行检索
     * 
     * @param templateModel
     * @param filePathModel
     * @param isDelete
     * @throws Exception
     */
    private void processTemplateRootDirs(Map templateModel, Map filePathModel, boolean isDelete) throws Exception {
        // 判断输出路径是否为空
        if (StringHelper.isBlank(getOutRootDir()))
            throw new IllegalStateException("'outRootDir' property  null.");
        // 判断templateRootDirs路径是否为空
        if (templateRootDirs.size() == 0)
            throw new IllegalStateException("'templateRootDirs' cannot empty");
        GeneratorException ge = new GeneratorException("generator occer error, Generator BeanInfo:" + BeanHelper.describe(this));
        for (int i = 0; i < this.templateRootDirs.size(); i++) {
            File templateRootDir = (File) templateRootDirs.get(i);
            List<Exception> exceptions = scanTemplatesAndProcess(templateRootDir, templateModel, filePathModel, isDelete);
            ge.addAll(exceptions);
        }
        if (!ge.exceptions.isEmpty())
            throw ge;
    }

    /**
     * 检索文件
     * 
     * @param templateRootDir
     * @param templateModel
     * @param filePathModel
     * @param isDelete
     * @return
     * @throws Exception
     */
    private List<Exception> scanTemplatesAndProcess(File templateRootDir, Map templateModel, Map filePathModel, boolean isDelete) throws Exception {
        if (templateRootDir == null)
            throw new IllegalStateException("'templateRootDir' must be not null");
        GLogger.println("-------------------load template from templateRootDir = '" + templateRootDir.getAbsolutePath() + "' outRootDir:"
                + new File(outRootDir).getAbsolutePath());

        // 获取目录下的所有文件
        List<File> srcFiles = FileHelper.searchAllNotIgnoreFile(templateRootDir);

        List<Exception> exceptions = new ArrayList<Exception>();
        for (int i = 0; i < srcFiles.size(); i++) {
            File srcFile = (File) srcFiles.get(i);
            try {
                if (isDelete) {
                    new TemplateProcessor().executeDelete(templateRootDir, templateModel, filePathModel, srcFile);
                } else {
                    new TemplateProcessor().executeGenerate(templateRootDir, templateModel, filePathModel, srcFile);
                }
            } catch (Exception e) {
                if (ignoreTemplateGenerateException) {
                    GLogger.warn("iggnore generate error,template is:" + srcFile + " cause:" + e);
                    exceptions.add(e);
                } else {
                    throw e;
                }
            }
        }
        return exceptions;
    }

    private class TemplateProcessor {
        private GeneratorControl gg = new GeneratorControl();

        private void executeGenerate(File templateRootDir, Map templateModel, Map filePathModel, File srcFile) throws SQLException, IOException,
                TemplateException {
            // 相对路径
            String templateFile = FileHelper.getRelativePath(templateRootDir, srcFile);
            // 查看是否是忽略文件
            if (GeneratorHelper.isIgnoreTemplateProcess(srcFile, templateFile, includes, excludes)) {
                return;
            }

            if (isCopyBinaryFile && FileHelper.isBinaryFile(srcFile)) {
                String outputFilepath = proceeForOutputFilepath(filePathModel, templateFile);
                GLogger.println("[copy binary file by extention] from:" + srcFile + " => " + new File(getOutRootDir(), outputFilepath));
                IOHelper.copyAndClose(new FileInputStream(srcFile), new FileOutputStream(new File(getOutRootDir(), outputFilepath)));
                return;
            }

            try {
                // 转换路径 mark
                String outputFilepath = proceeForOutputFilepath(filePathModel, templateFile);
                // 初始化 freemarker 信息
                initGeneratorControlProperties(srcFile, outputFilepath);
                processTemplateForGeneratorControl(templateModel, templateFile);
                if (gg.isIgnoreOutput()) {
                    GLogger.println("[not generate] by gg.isIgnoreOutput()=true on template:" + templateFile);
                    return;
                }
                if (StringHelper.isNotBlank(gg.getOutputFile())) {
                    generateNewFileOrInsertIntoFile(templateFile, gg, templateModel);
                }
            } catch (Exception e) {
                throw new RuntimeException("generate oucur error,templateFile is:" + templateFile + " => " + gg.getOutputFile() + " cause:" + e, e);
            }
        }

        private void executeDelete(File templateRootDir, Map templateModel, Map filePathModel, File srcFile) throws SQLException, IOException,
                TemplateException {
            String templateFile = FileHelper.getRelativePath(templateRootDir, srcFile);
            if (GeneratorHelper.isIgnoreTemplateProcess(srcFile, templateFile, includes, excludes)) {
                return;
            }
            String outputFilepath = proceeForOutputFilepath(filePathModel, templateFile);
            initGeneratorControlProperties(srcFile, outputFilepath);
            gg.deleteGeneratedFile = true;
            processTemplateForGeneratorControl(templateModel, templateFile);
            GLogger.println("[delete file] file:" + new File(gg.getOutputFile()).getAbsolutePath());
            new File(gg.getOutputFile()).delete();
        }

        private void initGeneratorControlProperties(File srcFile, String outputFile) throws SQLException {
            gg.setSourceFile(srcFile.getAbsolutePath());
            gg.setSourceFileName(srcFile.getName());
            gg.setSourceDir(srcFile.getParent());
            gg.setOutRoot(getOutRootDir());
            gg.setOutputEncoding(outputEncoding);
            gg.setSourceEncoding(sourceEncoding);
            gg.setMergeLocation(GENERATOR_INSERT_LOCATION);
            gg.setOutputFile(outputFile);
        }

        private void processTemplateForGeneratorControl(Map templateModel, String templateFile) throws IOException, TemplateException {
            templateModel.put("gg", gg);
            // Template template = getFreeMarkerTemplate(templateFile);
            // template.process(templateModel, IOHelper.NULL_WRITER);
        }

        /** 处理文件路径的变量变成输出路径 */
        private String proceeForOutputFilepath(Map filePathModel, String templateFile) throws IOException {
            String outputFilePath = templateFile;

            // TODO 删除兼容性的@testExpression
            int testExpressionIndex = -1;
            if ((testExpressionIndex = templateFile.indexOf('@')) != -1) {
                outputFilePath = templateFile.substring(0, testExpressionIndex);
                String testExpressionKey = templateFile.substring(testExpressionIndex + 1);
                Object expressionValue = filePathModel.get(testExpressionKey);
                if (expressionValue == null) {
                    System.err.println("[not-generate] WARN: test expression is null by key:[" + testExpressionKey + "] on template:[" + templateFile + "]");
                    return null;
                }
                if (!"true".equals(String.valueOf(expressionValue))) {
                    GLogger.println("[not-generate]\t test expression '@" + testExpressionKey + "' is false,template:" + templateFile);
                    return null;
                }
            }

            for (String removeExtension : removeExtensions.split(",")) {
                if (outputFilePath.endsWith(removeExtension)) {
                    outputFilePath = outputFilePath.substring(0, outputFilePath.length() - removeExtension.length());
                    break;
                }
            }
            // conf 的定义
            Configuration conf = GeneratorHelper.newFreeMarkerConfiguration(templateRootDirs, sourceEncoding, "/filepath/processor/");
            return FreemarkerHelper.processTemplateString(outputFilePath, filePathModel, conf);
        }

        /**
         * 获取模版内容
         * 
         * @param templateName
         * @return
         * @throws IOException
         */
        private Template getFreeMarkerTemplate(String templateName) throws IOException {
            Configuration conf = GeneratorHelper.newFreeMarkerConfiguration(templateRootDirs, sourceEncoding, templateName);
            return conf.getTemplate(templateName);
        }

        /**
         * 生成文件（更新或插入）
         * 
         * @param templateFile
         * @param outputFilepath
         * @param templateModel
         * @throws Exception
         */
        private void generateNewFileOrInsertIntoFile(String templateFile, GeneratorControl gg, Map templateModel) throws Exception {
            String outputFilepath = gg.getOutputFile();
            Template template = getFreeMarkerTemplate(templateFile);
            template.setOutputEncoding(gg.getOutputEncoding());
            // 生成文件的目录
            File absoluteOutputFilePath = FileHelper.parentMkdir(outputFilepath);
            if (absoluteOutputFilePath.exists()) {
                // 判断输出路径是否为空
                if (FileHelper.isExists(gg.getSourceFile() + ".include")) {
                    template = getFreeMarkerTemplate(templateFile + ".include");
                }
                StringWriter newFileContentCollector = new StringWriter();
                if (GeneratorHelper.isFoundInsertLocation(gg, template, templateModel, absoluteOutputFilePath, newFileContentCollector)) {
                    GLogger.println("[insert]\t generate content into:" + outputFilepath);
                    IOHelper.saveFile(absoluteOutputFilePath, newFileContentCollector.toString(), gg.getOutputEncoding());
                    return;
                }
            }

            if (absoluteOutputFilePath.exists() && !gg.isOverride()) {
                GLogger.println("[not generate]\t by gg.isOverride()=false and outputFile exist:" + outputFilepath);
                return;
            }

            GLogger.println("[generate]\t template:" + templateFile + " ==> " + outputFilepath);
            FreemarkerHelper.processTemplate(template, templateModel, absoluteOutputFilePath, gg.getOutputEncoding());
        }
    }
}
