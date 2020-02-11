package com.Training.Collections_Class;
import java.util.*;

public class CollectionsDemo_replaceAll {
	   public static void main(String[] args) {
		   // create vector
		   Vector vector = new Vector();
		      
		   // populate the vector
		   vector.add("R");
		   vector.add("B");
		   vector.add("R");
			   
		   System.out.println("Initial values are :"+vector);
		      
		   // replace 'R' with 'Replace All'
		   Collections.replaceAll(vector, "R", "Replace All");
		      
		   System.out.println("Value after replace :"+ vector);
		   }

}
