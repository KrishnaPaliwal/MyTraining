package com.myTraining.General_Program;

public class StaticMethodOverload {
	
	public static void method(String s)
	{
		System.out.println("Overloaded method with argument String "+s);
	}
	
	public static void method(Integer I)
	{
		System.out.println("Static method with argument Integer " + I);
	}
	
	
	public static void method(Object obj)
	{
		System.out.println("Static method with argument Object " + obj);
	}
	
	
	
	public static void main(String a[])
	{
		StaticMethodOverload.method(null);	

		
	}

}
