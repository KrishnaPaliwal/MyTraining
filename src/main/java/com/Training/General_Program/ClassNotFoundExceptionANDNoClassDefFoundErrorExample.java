package com.Training.General_Program;

public class ClassNotFoundExceptionANDNoClassDefFoundErrorExample
	{
	    public static void main(String[] args)
	    {
	        try
	        {
	            Class.forName("oracle.jdbc.driver.OracleDriver"); // java.lang.ClassNotFoundException
	            // Class.forName("com.Training.File_Handling.CreateDirectory"); // It will load
	        }
	        catch (ClassNotFoundException e)
	        {
	            e.printStackTrace();
	        }
	    }
	}

class ToBeRemoved
{
	public void test()
	{
		System.out.println("fsfsfsf");
	}
}
 
class NoClassDefFoundErrorExample
{
    public static void main(String[] args)
    {
    	//If you remove the ToBeRemoved.class file and run the NoClassDefFoundErrorExample.class 
    	// file, Java Runtime System will throw NoClassDefFoundError like below
    	
    	ToBeRemoved a = new ToBeRemoved();
    	//a.test();	
    }
}

