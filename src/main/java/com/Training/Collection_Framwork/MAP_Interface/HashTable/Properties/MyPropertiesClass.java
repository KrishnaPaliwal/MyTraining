package com.Training.Collection_Framwork.MAP_Interface.HashTable.Properties;

import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.util.Properties;

public class MyPropertiesClass 
{
	public static void main(String... args) throws IOException
	{

		Properties prop = new Properties();
		
//setProperty method
		prop.setProperty("color", "blue");
		
//getProperty method
		String st = prop.getProperty("color");
		System.out.println(st); // blue

// load method		
		Reader reader = null;
		prop.load(reader);
		
// list method
		PrintStream out = null;
		prop.list(out);

		String S = MyPropertiesClass.class.getName();
	}
	
}
