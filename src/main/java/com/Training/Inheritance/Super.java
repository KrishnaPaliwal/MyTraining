package com.myTraining.Inheritance;

public interface Super {
	int a=5;
	
	
	default void method()
	{
		System.out.println("in interface Super");
	}


}
