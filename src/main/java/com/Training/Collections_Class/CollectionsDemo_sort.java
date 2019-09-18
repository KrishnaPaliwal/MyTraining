package com.myTraining.Collections_Class;

import java.util.*;

public class CollectionsDemo_sort {
	   public static void main(String args[]) {
		   // create an array of string objs
		   String init[] = { "One", "Two", "Three", "One", "Two", "Three" };
		      
		   // create one list
		   List list = new ArrayList(Arrays.asList(init));
		      
		   System.out.println("List value before: "+list);
		      
		   // sort the list
		   Collections.sort(list);
		      
		   System.out.println("List value after sort: "+list);
		   }

}
