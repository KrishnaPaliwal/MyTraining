package com.Training.General_Program;

public class ApplicationExceptionClass {
	
	public void method1() throws MyUnCheckedException, MyCheckedException
	{
		throw new MyUnCheckedException("Exception occured");
	}
	public static void main(String args[]) throws MyUnCheckedException, MyCheckedException
	{
		
		ApplicationExceptionClass obj = new ApplicationExceptionClass();
		try{
		obj.method1();
		}
		
		catch(MyUnCheckedException e){
			throw e;
		}
		
		
		
	}
	

}
