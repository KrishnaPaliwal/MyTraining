package com.myTraining.Overriding;

public class Employee {
	int salary=900000;
	public int getSalary()
	{	
		System.out.println("Salar from Employee Class and getSalary() method");
		return salary;
	}
	
	public int getSalary(int n)
	{	System.out.println("Salar from Employee Class and getSalary(int n) method");
		return n;	
	}

}
