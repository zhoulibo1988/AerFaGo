package sine.framework.generator.newGen;

import java.util.HashMap;
import java.util.Map;

import sine.framework.generator.newGen.GeneratorManager.GeneratorContext;
import sine.framework.generator.util.BeanHelper;

public class GeneratorModelUtils {

	/**
	 * 反射表信息
	 * @param table
	 * @return
	 */
	public static GeneratorModel newFromTable(TableInfo table) {
		Map<Object,Object> templateModel = new HashMap<Object,Object>();
		templateModel.put("table", table);
		setShareVars(templateModel);

		Map<Object,Object> filePathModel = new HashMap<Object,Object>();
		setShareVars(filePathModel);
		filePathModel.putAll(BeanHelper.describe(table));
		return new GeneratorModel(templateModel, filePathModel);
	}

	public static GeneratorModel newFromMap(Map<Object,Object> params) {
		Map<Object,Object> templateModel = new HashMap<Object,Object>();
		templateModel.putAll(params);
		setShareVars(templateModel);

		Map<Object,Object> filePathModel = new HashMap<Object,Object>();
		setShareVars(filePathModel);
		filePathModel.putAll(params);
		return new GeneratorModel(templateModel, filePathModel);
	}

	/*
	 * 设置一些额外的信息
	 */
	public static void setShareVars(Map<Object,Object> templateModel) {
		templateModel.putAll(GeneratorProperties.getProperties());
		// templateModel.putAll(System.getProperties());
		// templateModel.put("env", System.getenv());
		// templateModel.put("now", new Date());
		templateModel.putAll(GeneratorContext.getContext());
	}

}
