package com.Training.System_Class;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.InputStream;
import java.lang.System;


public class MySystemClass {
	public static void main(String [] args)
	{
		PrintStream ps = System.out;
		ps.println("this is output message");
		
		InputStream is = System.in;
		
		PrintStream pse = System.err;
		
		pse.println("This is error");
		
		try {
			System.setOut(new PrintStream(new FileOutputStream("log.txt")));
			System.out.println("Now the output is redirected here default!");	// path : D:\JAVA\JAVATraningDirectory\Core_Java

//setOut method			
			System.setOut(new PrintStream(new FileOutputStream("D:\\JAVA\\JAVA STUDY MATERIAL\\log.txt")));
			System.out.println("Now the output is redirected here to D:\\JAVA\\JAVA STUDY MATERIAL!"); //Path : D:\\JAVA\\JAVA STUDY MATERIAL\\
		} 
		
		catch (Exception e) {

			e.printStackTrace();
		}
		


		
		
	}
}
