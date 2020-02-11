package com.Training.Collections_Class;

import java.util.*;

public class CollectionsDemo_newSetFromMap {
	   public static void main(String args[]) { 
		   // create map
		   Map<String, Boolean> map = new WeakHashMap<String, Boolean>();
		   
		   // create a set from map
		   Set<String> set = Collections.newSetFromMap(map); 
		      
		   // add values in set
		   set.add("Java"); 
		   set.add("C");
		   set.add("C++");
		      
		   // set and map values are
		   System.out.println("Set is: " + set);
		   System.out.println("Map is: " + map);
		   }

}
