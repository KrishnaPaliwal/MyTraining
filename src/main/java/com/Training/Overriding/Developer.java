package com.myTraining.Overriding;

public class Developer extends Employee {
	
	int salary = 500000;
	
	@Override
	public int getSalary()
	{
		System.out.println("Salar from Developer Class and getSalary() method");
		return this.salary;
		
	}
	
	@Override
	public int getSalary(int n)
	{
		System.out.println("Salar from Developer Class and getSalary() method");
		return n;
		
	}

}
