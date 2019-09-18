package com.myTraining._ETLProcessing.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.myTraining._ETLProcessing.extract.ETLExtractor;
import com.myTraining._ETLProcessing.processor.ETLProcessor;
import com.myTraining._ETLProcessing.processor.SourceDestForFileETL;
import com.myTraining._ETLProcessing.trasform.CapitalWordTransformer;
import com.myTraining._ETLProcessing.trasform.ITransformer;
import com.myTraining._ETLProcessing.trasform.WordCounterTransformer;


public class ETLProcessingTester {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String inputDirectory = br.readLine();
	//	String inputDirectory = "D://ETLProcessingFIles";
		SourceDestForFileETL inputOutput = new SourceDestForFileETL(inputDirectory);
		List<ITransformer> transformers = new ArrayList<ITransformer>();
		transformers.add(new CapitalWordTransformer(inputOutput));
		transformers.add(new WordCounterTransformer(inputOutput));
		ETLProcessor fileETL =  new ETLProcessor(new ETLExtractor(inputOutput),transformers);
		fileETL.runETL();
	}
}
 	