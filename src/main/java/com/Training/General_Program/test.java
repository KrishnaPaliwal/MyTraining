package com.Training.General_Program;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class test {
	public static void main(String []args)
	{
		List<Employee> list = new ArrayList<Employee>();
		list.add(new Employee("BKrishna", 800000));
		list.add(new Employee("ADurga", 300000));
		list.add(new Employee("CPrerita", 600000));

		
		Collections.sort(list, new MyComparator());
		
		for(Employee e : list)
		{
			System.out.println("name "+e.getName()+" salary "+e.getSalary());
		}
		
		
	}
}
