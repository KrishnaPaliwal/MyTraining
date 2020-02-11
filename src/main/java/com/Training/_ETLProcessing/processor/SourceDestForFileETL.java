package com.Training._ETLProcessing.processor;

import java.util.Date;
/**
 * This is input output specifier class for File ETL
 * @author krishna
 *
 */
public class SourceDestForFileETL {
	private String inputDirectory;
	private String ouputDirectoryCapitalizeTrasformation;
	private String outputDirectoryCountTranformer;
	public SourceDestForFileETL(String inputDirectory) {
		super();
		this.inputDirectory = inputDirectory;
		String date =  ""+  new Date().getTime();
		this.ouputDirectoryCapitalizeTrasformation = inputDirectory +"//"+ "CapitalizeOutput"+ date  ;
		this.outputDirectoryCountTranformer =inputDirectory + "//"+"CountOutput" + date;;
	}
	public String getInputDirectory() {
		return inputDirectory;
	}
	public String getOuputDirectoryCapitalizeTrasformation() {
		return ouputDirectoryCapitalizeTrasformation;
	}
	public String getOutputDirectoryCountTranformer() {
		return outputDirectoryCountTranformer;
	}

	
}
