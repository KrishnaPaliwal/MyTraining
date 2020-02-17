package com.Training.File_Handling;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ReadWriteFileExample {

	public static void main(String [] args) {
		
		try (
				FileReader fReader = new FileReader(new File("C:\\Program Files\\Java\\jdk1.8.0_112\\COPYRIGHT")); 
				BufferedReader br = new BufferedReader(fReader);		
				FileWriter fWriter = new FileWriter(new File("F:\\COPYRIGHT_new"));
				BufferedWriter bw = new BufferedWriter(fWriter);
				) {

		String line=null;

		while((line=br.readLine()) != null) {
			
			System.out.println(line);
			bw.write(line);
			bw.write("\n");
		}
		
	}  catch(Exception e) {
		e.printStackTrace();
	}
		// Another way is below

		try (
				FileReader fReader = new FileReader(new File("C:\\Program Files\\Java\\jdk1.8.0_112\\COPYRIGHT")); 
				FileWriter fWriter = new FileWriter(new File("F:\\COPYRIGHT_newX"));
				) {

		int line;

		while((line=fReader.read()) != -1) {
			
			System.out.println(line);
			fWriter.write(line);
		}
		
	}  catch(Exception e) {
		e.printStackTrace();
	}
	
		
}
}