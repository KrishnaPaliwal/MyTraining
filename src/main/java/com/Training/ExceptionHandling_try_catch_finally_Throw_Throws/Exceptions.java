package com.myTraining.ExceptionHandling_try_catch_finally_Throw_Throws;

import java.util.zip.ZipFile;

public class Exceptions{
	public static void main(String Args[]) throws Exception{
		int[] array = new int[3];
		try
		{
			for(int i=0;i<4;++i)
			{
				array[i] = i;
			}
			array[0] = 2/0;
			 
			System.out.println(array);
		}
		catch(ArithmeticException e)
		{
		
			//			System.out.println("filIn: " + e.fillInStackTrace());
//			System.out.println("cause: " + e.getCause());
//			System.out.println("local: " + e.getLocalizedMessage());
//			System.out.println("messa: " + e.getMessage());
//			System.out.println("trace: " + e.getStackTrace());
//			System.out.println();
//			System.out.println();
//			System.out.print("trace: ");
			e.printStackTrace();
//			System.out.println();
//			System.out.print("string: ");
//			e.toString();
//			System.out.println();
//			System.out.println();
			//printed just to inform that we have entered the catch block
			//System.err.println("Oops, we went to far, better go back to 0!");
//			throw (Exception) new Exception().initCause(e);
		}
		

		
	}
}

