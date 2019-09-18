package com.myTraining.INNER_CLASSES__Member_Nested_Local_Anonymous;

public class NestedStaticClass {

	static class NestedClass
	{
		int x=10;
		
		void method()
		{
			System.out.println("This is Nestesed static class"+x);
		}
		
	}
		
	public static void main(String args[])
	{
		NestedStaticClass.NestedClass nestedclassobject = new NestedStaticClass.NestedClass();
		
		nestedclassobject.method();
	}	
}


