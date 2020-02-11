package com.Training._ETLProcessing.system.exception;

public class NotDirectoryETLException extends SystemException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotDirectoryETLException(String path) {
		super("This is not a directory" + path);
	}

}
