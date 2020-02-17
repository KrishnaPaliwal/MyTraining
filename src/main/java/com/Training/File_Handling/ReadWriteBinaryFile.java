package com.Training.File_Handling;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ReadWriteBinaryFile {

	public static void main(String [] args) throws IOException {

		FileInputStream fis = new FileInputStream("files/CISCO holiday calendar 2020.PNG");
		FileOutputStream fos = new FileOutputStream("files/CISCO holiday calendar 2020_new.PNG");
		int c;
		while((c=fis.read()) != -1) {
			fos.write(c);			
		}
		
		fos.close();
	}
}
