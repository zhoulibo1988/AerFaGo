package sine.framework.generator.newGen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;

import sine.framework.generator.util.GLogger;


public class GeneratorPrintUtils {
	public void printExceptionsSumary(String msg, String outRoot,List<Exception> exceptions) throws FileNotFoundException {
		File errorFile = new File(outRoot, "generator_error.log");
		if (exceptions != null && exceptions.size() > 0) {
			System.err.println("[Generate Error Summary] : " + msg);
			PrintStream output = new PrintStream(
					new FileOutputStream(errorFile));
			for (int i = 0; i < exceptions.size(); i++) {
				Exception e = exceptions.get(i);
				System.err.println("[GENERATE ERROR]:" + e);
				if (i == 0)
					e.printStackTrace();
				e.printStackTrace(output);
			}
			output.close();
			System.err
					.println("***************************************************************");
			System.err.println("* " + "* 输出目录已经生成generator_error.log用于查看错误 ");
			System.err
					.println("***************************************************************");
		}
	}

	public void printBeginProcess(String displayText, boolean isDatele) {
		GLogger.println("***************************************************************");
		GLogger.println("* BEGIN "
				+ (isDatele ? " delete by " : " generate by ") + displayText);
		GLogger.println("***************************************************************");
	}

	public void printAllTableNames(List<TableInfo> tables) throws Exception {
		GLogger.println("***************************************************************");
		GLogger.println("----All TableNames BEGIN----");
		for (int i = 0; i < tables.size(); i++) {
			String sqlName =(tables.get(i)).getTableName();
			GLogger.println("Table name: " + sqlName);
		}
		GLogger.println("-----All TableNames END-----");
		GLogger.println("***************************************************************");

	}
}
