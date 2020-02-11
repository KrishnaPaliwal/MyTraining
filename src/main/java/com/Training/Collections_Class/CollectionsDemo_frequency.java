package com.Training.Collections_Class;

import java.util.*;
public class CollectionsDemo_frequency {

	   public static void main(String args[]) {
		   // create array list object       
		   List arrlist = new ArrayList();
		      
		   // populate the list
		   arrlist.add("A");
		   arrlist.add("B");
		   arrlist.add("C");
		   arrlist.add("C");
		   arrlist.add("C");      
		           
		   // check frequensy of 'C'
		   int freq = Collections.frequency(arrlist, "C");
		      
		   System.out.println("Frequency of 'C' is: "+freq);    
		   }    
}