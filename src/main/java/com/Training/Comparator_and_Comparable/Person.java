package com.Training.Comparator_and_Comparable;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Person implements Comparable<Person> {

	  public String name;
	  public String lastName;
	  public int salary;
	 
	  public Person(String name, String lastName, int salary){
	     this.name=name;
	     this.lastName=lastName;
	     this.salary = salary;
	  }
	  public int getSalary() {
		return salary;
	}
	public String getName(){
	    return name;
	  }
	  public String getLastName(){
	    return lastName;
	  }
	  
	  public int compareTo(Person obj){
		  // Comapre integer value in ascending order
		  return this.salary-obj.getSalary();
		  
		  // This code is to compare Strings
		   // return this.salary.compareTo(p.getSalary());
		  
		  // This is another way to compare integer value is descending order
/*		  if (this.salary<=p.salary)
			  {
			  System.out.println("this.salary "+this.salary+" <= p.salary "+p.salary);
			  return 1;
			  }
		  
		  if (this.salary>=p.salary)
		  	{
			  System.out.println("this.salary "+this.salary+" > p.salary "+p.salary);
			  return -1;
		  }
		  return 0;
		  */
		  
	  }
	  
	  @Override
	  public String toString()
	  {	
		  
		  return MessageFormat.format("Name : {0}, Age: {1}, Salary: {2}" , name, lastName, salary);
	  }
}
