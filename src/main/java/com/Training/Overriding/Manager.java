package com.Training.Overriding;

public class Manager extends Employee {
	
	int salary = 200000;
	@Override
	public int getSalary()
	{	
		System.out.println("Salar from Manager and Class getSalary() method");
		return this.salary;
	}

}
