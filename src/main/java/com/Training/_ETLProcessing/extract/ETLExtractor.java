package com.Training._ETLProcessing.extract;

import java.io.File;
import java.io.IOException;

import com.Training._ETLProcessing.processor.SourceDestForFileETL; 
import com.Training._ETLProcessing.system.exception.IOETLException;
import com.Training._ETLProcessing.system.exception.NotDirectoryETLException;
import com.Training._ETLProcessing.util.ETLHelper;
/**
 * This is ETL extractor it extracts files from given input directory
 * @author krishna
 *
 */
public class ETLExtractor implements    IExtractor{  

	private SourceDestForFileETL inputOutput;  
	
	public ETLExtractor(SourceDestForFileETL inputOutput) {
		this.inputOutput = inputOutput;
	}

	public String getInputDirectory()   {
		return inputOutput.getInputDirectory();
	}

	

	@Override
	public void extract() throws NotDirectoryETLException, IOETLException {
			File[] inputFiles = ETLHelper.getListOfFiles(getInputDirectory());
			for(File file : inputFiles) {
				try {
					file.getCanonicalPath();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					throw new IOETLException(e.getMessage());
				}
			}
			System.out.println( "Extraction completed");
			
		
		
	}

}
