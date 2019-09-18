package com.myTraining.ExceptionHandling_try_catch_finally_Throw_Throws;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ExceptionHandling {

	public static void main(String []args) throws FileNotFoundException, IOException

	{
		try 
		{
			testException(-5);
			testException(-10);
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		catch(IOException e)
		{
			System.out.println("Incorrect input");
		}
		
		finally
		{
		System.out.println("Resources released");	
		}
		testException(15);		
	}
	
	public static void testException(Integer I) throws FileNotFoundException,IOException 
	{
		if (I<0)
		{
			FileNotFoundException fnfException = new FileNotFoundException("Please provide the correct file");
			
			throw fnfException;
		}
		
		else if (I>10)
		{
			throw new IOException("IO Exception Logged");
		}
	}
	
}