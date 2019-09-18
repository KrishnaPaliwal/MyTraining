package com.myTraining.General_Program;

public class Factorial {
	
	int fact(int a)
	{
		if (a<1)
			return 1;
		else 
			return a=a*fact(a-1);
	}
	
	public static void main(String args[])
	{
		Factorial f= new Factorial();
		int X = f.fact(4);
		
		
		System.out.println(X);
	}
}
