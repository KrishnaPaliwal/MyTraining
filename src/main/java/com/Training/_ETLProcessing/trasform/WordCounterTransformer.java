package com.Training._ETLProcessing.trasform;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.Training._ETLProcessing.processor.SourceDestForFileETL;
import com.Training._ETLProcessing.system.exception.IOETLException;
import com.Training._ETLProcessing.system.exception.NotDirectoryETLException;
import com.Training._ETLProcessing.system.exception.SystemException;
import com.Training._ETLProcessing.util.ETLHelper;
/**
 * 
 * @author krishna
 *
 */
public class WordCounterTransformer implements ITransformer {
	SourceDestForFileETL inputOutput;

	public WordCounterTransformer(SourceDestForFileETL inputOutput) {
		this.inputOutput = inputOutput;
	}

	@SuppressWarnings("resource")
	@Override
	public void transform() throws IOETLException, NotDirectoryETLException {
		String inputDirectory = inputOutput
				.getOuputDirectoryCapitalizeTrasformation();
		String opDirectory = inputOutput.getOutputDirectoryCountTranformer();
		File[] inputFiles = ETLHelper.getListOfFiles(inputDirectory);
		ETLHelper.createOutputDirectory(opDirectory);

		for (File file : inputFiles) {
			Map<String, Integer> map = new ConcurrentHashMap<>();//new LinkedHashMap<String, Integer>();
			File outputFile = new File(opDirectory + "//" + file.getName());
			BufferedReader br = null;
			String sCurrentLine;
			BufferedWriter bw = null;

			try {

				outputFile.createNewFile();

				FileWriter fw = new FileWriter(outputFile);
				bw = new BufferedWriter(fw);
				br = new BufferedReader(new FileReader(file));
				while ((sCurrentLine = br.readLine()) != null) {
					String[] words = sCurrentLine.split(" ");
					for (String word : words) {
						if (map.containsKey(word)) {
							map.put(word, map.get(word) + 1);
						} else {
							map.put(word, 1);
						}
					}

				}
				for (Map.Entry<String, Integer> entry : map.entrySet()) {
					bw.write(entry.getKey() + "-> " + entry.getValue());
					bw.newLine();

				}
				bw.flush();
				bw.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				throw new IOETLException(e.getMessage());
			}

		}
	}

	@Override
	public String transform(String input) throws SystemException {
		// TODO Auto-generated method stub
		return null;
	}
}
