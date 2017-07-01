package sine.framework.generator;

import sine.framework.generator.newGen.GeneratorManager;
import sine.framework.generator.newGen.GeneratorProperties;

/**
 * 
 * @ClassName: GeneratorMain
 * @Description: main
 * @author buyuer
 * @date 2015年10月9日 上午11:00:22
 * 
 */
public class GeneratorMain {
	/**
	 * 请直接修改以下代码调用不同的方法以执行相关生成任务.
	 */
	// @SuppressWarnings({ "static-access"})
	public static void main(String[] args) throws Exception {
		String templateRootDir = "src/main/java/template";
		GeneratorManager generatorManager = new GeneratorManager();
		//generatorManager.printAllTableNames();
		// generatorManager.generateByTables("*", templateRootDir);
		generatorManager.generateByTables("weixin_business_info", templateRootDir);
		//generatorManager.generateByTables("gs_recommend", templateRootDir);
		// 打开文件夹
		 Runtime.getRuntime().exec("cmd.exe /c start "+GeneratorProperties.getRequiredProperty("outRoot"));
	}
}
