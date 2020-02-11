package com.Training.Collections_Class;
import java.util.*;

public class CollectionsDemo_unmodifiableSet {
	   public static void main(String[] s) {
		   // create set
		   Set<String> set = new LinkedHashSet<String>();
		      
		   // populate the set
		   set.add("aWelcome");
		   set.add("bto");
		   set.add("cTP");
		   //Collections.sort(set);
		      
		   System.out.println("Initial set value: "+set);
		      
		   // create unmodifiable set
		   Set unmodset = Collections.unmodifiableSet(set);

		   // try to modify the set
		   //unmodset.add("Hello");
		   }

}
