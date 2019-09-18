package com.myTraining.Comparator_and_Comparable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestComparableFunctionality {
	
	public static void main(String arg[]){
		
		List<Person> myList = new ArrayList<Person>();
 
	    myList.add(new Person("Robert","USA", 8000));
	    myList.add(new Person("Andy","UK", 3000));
	    myList.add(new Person("AHarish","India", 4000));
	    myList.add(new Person("BHarish","India", 6000));
	    myList.add(new Person("CHarish","India", 5000));
	    myList.add(new Person("CHarish","India", 4000));
	    Collections.sort(myList);
	    
	    for(Person person : myList){
	    	System.out.println(person);
	 //    System.out.println("My name is "+person.getName()+" I am from "+person.getLastName()+ " and my salary is "+person.getSalary());
	    }
	  }
}
