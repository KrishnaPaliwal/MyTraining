package com.myTraining.String_Class;

public class StringConcatExample {

	public static void main(String ff[])
	
	{
		String s = "Testing";

		method(s);
		System.out.println(s);
	}

	private static void method(String s)  {
		s = new String(s+"Append");
		System.out.println(s);
	}
}
