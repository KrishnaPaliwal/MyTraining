package com.Training.General_Program;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

public class WaysToCreateObjects {
	
	@SuppressWarnings("unused")
	public static void main(String [] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, CloneNotSupportedException, IOException
	{
		// 1st way Using new keyword
		SampleClass obj1 = new SampleClass();
		
		// 2nd way Using Class.forName()
		SampleClass obj2 = (SampleClass) Class.forName("com.Training.General_Program.SampleClass").newInstance();

		// 3rd way Using clone()
		SampleClass obj3 = (SampleClass)obj1.clone();
		
		// 4th way Using object deserialization
		InputStream anInputStream = null; 
		ObjectInputStream inStream = new ObjectInputStream(anInputStream ); 
		SampleClass obj4 = (SampleClass) inStream.readObject();
	}
}



class SampleClass implements Cloneable
{

	public void myTestMethod()
	{
		
	} 
	
	public Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}
}