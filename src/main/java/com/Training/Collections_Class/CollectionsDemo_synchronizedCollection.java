package com.Training.Collections_Class;
import java.util.*;

public class CollectionsDemo_synchronizedCollection {

	   public static void main(String[] args) {
	   // create vector object 
	   Vector<String> vector = new Vector<String>();
	      
	   // populate the vector
	   vector.add("1");
	   vector.add("2");
	   vector.add("3");
	   vector.add("4");
	   vector.add("5");
	      
	   // create a synchronized view
	   Collection<String> c = Collections.synchronizedCollection(vector);
	     
	   System.out.println("Sunchronized view is :"+c);
	   }

}
