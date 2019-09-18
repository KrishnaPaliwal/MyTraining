package com.myTraining.Inheritance;

public interface A extends Super{

	int a=10;
	
	default void method()
	{
		System.out.println("in interface A");
	}
	
	
}
