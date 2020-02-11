package com.Training.File_Handling;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class ReadANDWrite {
	
	public static void main(String... args)
	{

		try
  		{
  		//Reading file and writing line by line using Scanner class and on console
  			FileInputStream fis1 = new FileInputStream("D:\\JAVA\\File Input and OutputStream\\input1.txt");      			
  			Scanner sc1 = new Scanner(fis1);
  			while(sc1.hasNextLine())
  					{
  				System.out.println(sc1.nextLine());
  			}
  			
  		//-----------------------------------------------------------------------------------      			
//System.in.read()
  		//Reading first character of input file using System.in.read()
  			FileInputStream fis2 = new FileInputStream("D:\\JAVA\\File Input and OutputStream\\input2.txt");
  			System.setIn(fis2);
			char c = (char) System.in.read();
			System.out.print(c); //prints the first character from input stream
		
			
			//-----------------------------------------------------------------------------------
//FileInputStream.read  		
		//Reading character by character complete file using FileInputStream.read method
   			FileInputStream fis3 = new FileInputStream("D:\\JAVA\\File Input and OutputStream\\input3.txt");
			int content1;
			
			while ((content1 = fis3.read()) != -1) {
			// convert to char and display it
			System.out.print((char) content1);
			}			
	  	
		//-----------------------------------------------------------------------------------
			File file = new File("D:\\JAVA\\File Input and OutputStream\\input4.txt");
			
			FileInputStream fis4 = new FileInputStream(file);
			int content2;
			
			while ((content2 = fis4.read()) != -1) {
			// convert to char and display it
			System.out.print("ttrytyhtyhtyh"+(char) content1);
			}				
			
  		//-----------------------------------------------------------------------------------
			FileInputStream fis5 = new FileInputStream("D:\\JAVA\\File Input and OutputStream\\input5.txt");
			
			//write on the file 
			FileOutputStream fos = new FileOutputStream("D:\\JAVA\\File Input and OutputStream\\server.log");
			
			int content3;
			while ((content3 = fis5.read()) != -1) {
				fos.write(fis5.read());
			}
		//------------------------------------------------------------------------------

  		
  		}	

  		catch(IOException e){
  			e.printStackTrace();
  		}
	}		
}

