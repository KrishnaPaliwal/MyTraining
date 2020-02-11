package com.Training.File_Handling;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

/*
 * Creating Directories:
 * There are two useful File utility methods, which can be used to create directories:
 * The mkdir( ) method creates a directory, returning true on success and false on failure.
 * Failure indicates that the path specified in the File object already exists, or that
 * the directory cannot be created because the entire path does not exist yet.
 * The mkdirs() method creates both a directory and all the parents of the directory.
 */
public class CreateDirectory {

	public static void main(String args[]) {
		//String dirname = "D:/JAVA/File Input and OutputStream/New folder by mkdirs method/g/g/g/g/g/";
		File file1 = new File("D:/JAVA/File Input and OutputStream/New folder by mkdirs method/g/g/g/g/");
		
// mkdirs method Create directories now.
		file1.mkdirs();
		
		File file2 = new File("D:/JAVA/File Input and OutputStream/New folder by mkdir method/");

// mkdir method Create one directory only.
		file2.mkdir();
		File file3 = new File("D:/JAVA/File Input and OutputStream/Krishna6.docx");
		try {
			System.out.println(file3.createNewFile());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Please provide correct path");
		}
		
		System.out.println(file3.getPath());
		
		file3.getAbsoluteFile();
		 String fileSeparator = System.getProperty("file.separator");
	
	}
	
	public void methodX()
	{
		System.out.println("dsfsfafsd");
	}
}

