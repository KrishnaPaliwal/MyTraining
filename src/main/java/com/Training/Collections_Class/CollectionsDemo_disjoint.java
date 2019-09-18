package com.myTraining.Collections_Class;

import java.util.*;

public class CollectionsDemo_disjoint {

	   public static void main(String args[]) {
		   // create two lists    
		   List<String> srclst = new ArrayList<String>(5);
		   List<String> destlst = new ArrayList<String>(10);
		      
		   // populate two lists
		   srclst.add("Java");
		   srclst.add("is");
		   srclst.add("best");
		      
		   destlst.add("C++");
		   destlst.add("is");
		   destlst.add("older");      
		      
		   // check elements in both collections, return false if common elements are present
		   boolean iscommon = Collections.disjoint(srclst, destlst);
		      
		   System.out.println("No commom elements: "+iscommon);    
		   }    

}
