package com.Training.Comparator_and_Comparable;


import java.util.Comparator;
import java.util.List;

import static java.text.MessageFormat.format;

public class Employee {

	public Employee(){
		
	}

	private String name;
	private int age;
	private int salary;
	private String department;

	public void staticmethod(){}
	
	public Employee(String name, int age, int salary, String department) {
		this.name = name;
		this.age = age;
		this.salary = salary;
		this.department = department;
	}
	
	public String getName() {
	return name;
	}

	public int getAge() {
	return age;
	}

	public int getSalary() {
	return salary;
	}

	public String getDepartment() {
	return department;
	}

	public static class EmployeeNameComparator implements Comparator<Employee> {

		  public int compare(Employee p1, Employee p2){

			   return p1.name.compareTo(p2.name);
			  }
	}
	
	public static class EmployeeAgeComparator implements Comparator<Employee> {

		@Override
		public int compare(Employee p1, Employee p2) {
			
			return p1.getAge()-p2.getAge();
		}

	}
	
	public class EmployeeSalaryComparator implements Comparator<Employee> {

		@Override
		public int compare(Employee p1, Employee p2) {
			
			return p1.getSalary()-p2.getSalary();
		}

	}

	@Override
	public String toString() {
		return format("Name: {0} AGE: {1} SALARY: {2} DEPT: {3}", name, age, salary, department);
	}

	public static void printEmployees(List<Employee> employeeList) {
		for (Employee employee : employeeList) {
			System.out.println(employee);
		}
	}
}


