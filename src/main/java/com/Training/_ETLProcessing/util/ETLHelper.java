package com.Training._ETLProcessing.util;

import java.io.File;
import java.io.FilenameFilter;

import com.Training._ETLProcessing.system.exception.IOETLException;
import com.Training._ETLProcessing.system.exception.NotDirectoryETLException;
/**
 * This is file helper class
 * 
 * @author krishna
 *
 */
public class ETLHelper {
	public static File createOutputDirectory(String opDirectory)
			throws IOETLException {
		File outputDirectory = new File(opDirectory);
		// if(outputDirectory.exists()) {
		// outputDirectory.delete()
		// }

		if (!outputDirectory.mkdir()) {
			throw new IOETLException("Directory not created");
		}
		return outputDirectory;

	}
	
	public static File[] getListOfFiles(String inputDirectory) throws IOETLException, NotDirectoryETLException {
		File dir = new File(inputDirectory);
		if (dir.isDirectory()) {
			File[] inputFiles = dir.listFiles(new FilenameFilter() {
				public boolean accept(File dir, String name) {
					return name.endsWith(".txt");
				}
			});
			return inputFiles;
		} else {
			throw new NotDirectoryETLException(inputDirectory);

		}
	}
}
