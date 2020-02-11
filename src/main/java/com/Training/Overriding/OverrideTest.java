package com.Training.Overriding;

public class OverrideTest {
	
	public static void main(String args[])
	{
		Employee e = new Employee();
		System.out.println(e.getSalary());
		System.out.println(e.getSalary(900000));
		System.out.println(e.salary);
		
		Employee eM = new Manager();
		System.out.println(eM.getSalary());
		System.out.println(eM.getSalary(200000));
		System.out.println(eM.salary);
		
		Employee eD = new Developer();
		System.out.println(eD.getSalary());
		System.out.println(eD.getSalary(500000));
		System.out.println(eD.salary);
		System.out.println(((Manager)eD).getSalary(500000));
		
	}

}
