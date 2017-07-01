package sine.framework.generator.newGen;

import java.util.Map;

public class GeneratorModel implements java.io.Serializable {
	private static final long serialVersionUID = 6189847487868918669L;
	public Map<Object, Object> filePathModel;
	public Map<Object, Object> templateModel;

	public GeneratorModel(Map<Object, Object> templateModel,
			Map<Object, Object> filePathModel) {
		this.templateModel = templateModel;
		this.filePathModel = filePathModel;
	}
}
