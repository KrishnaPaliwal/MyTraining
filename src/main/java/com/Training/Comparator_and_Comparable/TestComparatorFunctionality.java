package com.myTraining.Comparator_and_Comparable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.myTraining.Comparator_and_Comparable.Employee.EmployeeSalaryComparator;

public class TestComparatorFunctionality {

		public static void main(String[] args) {
			List<Employee> employeeList = new ArrayList<Employee>();
			employeeList.add(new Employee("Victor", 39, 6000, "ENGINEERING"));
			employeeList.add(new Employee("Alvin", 35, 5000, "SALES"));
			employeeList.add(new Employee("SAM", 41, 6500, "ENGINEERING"));
			employeeList.add(new Employee("Piter", 60, 4000, "MARKETING"));

			System.out.println("******Before Sorting******");
			Employee.printEmployees(employeeList);

			// Using Static class 
			System.out.println("******After Comparator Sorting On Name******");
			Collections.sort(employeeList, new Employee.EmployeeNameComparator());
			Employee.printEmployees(employeeList);

			// Using static Class
			System.out.println("******After Comparator Sorting On Age******");
			Employee.EmployeeAgeComparator staticClassObject = new Employee.EmployeeAgeComparator();
			Collections.sort(employeeList, staticClassObject);
			Employee.printEmployees(employeeList);

			// Using Inner class
			Employee.EmployeeSalaryComparator  rt = new Employee().new EmployeeSalaryComparator();
			
			System.out.println("******After Comparator Sorting On Salary******");
			Collections.sort(employeeList, rt);
			Employee.printEmployees(employeeList);
		}


}
