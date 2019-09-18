package com.myTraining._ETLProcessing.trasform;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.myTraining._ETLProcessing.processor.SourceDestForFileETL;
import com.myTraining._ETLProcessing.system.exception.IOETLException;
import com.myTraining._ETLProcessing.system.exception.SystemException;
import com.myTraining._ETLProcessing.util.ETLHelper;

/**
 * This class is used to transform contents to uppewr case all files from input
 * directory to output directory apital
 * 
 * @author krishna
 *
 */
public class CapitalWordTransformer extends CapitalizeTransformer {
	SourceDestForFileETL inputOutput;
	static String outputDirectory;

	public CapitalWordTransformer(SourceDestForFileETL inputOutput) {
		this.inputOutput = inputOutput;
	}

	@Override
	public void transform() throws SystemException {
		String inputDirectory = inputOutput.getInputDirectory();
		String opDirectory = inputOutput
				.getOuputDirectoryCapitalizeTrasformation();

		File[] inputFiles = ETLHelper.getListOfFiles(inputDirectory);

		ETLHelper.createOutputDirectory(opDirectory);

		for (File file : inputFiles) {

			File outputFile = new File(opDirectory + "//" + file.getName());

			BufferedReader br = null;
			String sCurrentLine;

			try {
				outputFile.createNewFile();

				FileWriter fw = new FileWriter(outputFile);
				BufferedWriter bw = new BufferedWriter(fw);
				br = new BufferedReader(new FileReader(file));

				while ((sCurrentLine = br.readLine()) != null) {
					bw.write(super.transform(sCurrentLine));
					bw.newLine();
					bw.flush();

				}
				bw.close();

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				new IOETLException(e.getMessage());
			}

		}
	}

}
