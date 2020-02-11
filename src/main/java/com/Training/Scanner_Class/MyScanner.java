package com.Training.Scanner_Class;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MyScanner {

	   public static void main(String args[]) throws FileNotFoundException
	   {
	      int x;
	      System.out.println("Enter an integer ");

	      Scanner in1 = new Scanner(System.in);
	      
	      x = in1.nextInt();
	      
	      System.out.println("You entered a number.\t"+x);
	      
	      String st;
	      System.out.println("Enter a string ");
	      Scanner in2 = new Scanner(System.in);
	      st = in2.next();
	      
	      System.out.println("You entered a String .\t"+st);
	      
	      

	      // Get the in put from file and write on console
	      FileInputStream fis1 = new FileInputStream("D:\\JAVA\\File Input and OutputStream\\input1.txt");      			
	      Scanner sc1 = new Scanner(fis1);
	      while(sc1.hasNextLine())
	      {
	    	  System.out.println(sc1.nextLine());
	      }
  			
	   }
	}

