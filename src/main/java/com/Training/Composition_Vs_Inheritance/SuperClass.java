package com.Training.Composition_Vs_Inheritance;

public abstract class SuperClass 
{
	
	public abstract void doSomthing();

}	

class SubClassA extends SuperClass 
{
	public void doSomthing()
	{
		System.out.println("doSomething implementation of A");
	
	}

	public void methodA()
	{
		
	}
}

class SubClassB extends SuperClass
{

	public void doSomthing() 
	{
		System.out.println("doSomething implementation of B");
		
	}
	
	public void methodB()
	{
		
	}
	
}

class SubClassA_and_B 
{
	SubClassA objA = new SubClassA();
	SubClassB objB = new SubClassB();
	
	public void test()
	{
		objA.doSomthing();
		objB.doSomthing();
	}
}
