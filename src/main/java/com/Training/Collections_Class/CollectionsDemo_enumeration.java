package com.Training.Collections_Class;

import java.util.*;
public class CollectionsDemo_enumeration {
	   public static void main(String args[]) {
		   // create array list object       
		   List arrlist = new ArrayList();
		      
		   // populate the list
		   arrlist.add("A");
		   arrlist.add("B");
		   arrlist.add("C");  
		      
		   // create Enumeration
		   Enumeration e = Collections.enumeration(arrlist);
		      
		   System.out.println("Print the enumeration");
		      
		   while(e.hasMoreElements()){
		   System.out.println("Value is: "+e.nextElement());
		   }
		   }    

}
