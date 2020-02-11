	package com.Training.Collections_Class;

import java.util.*;

public class CollectionsDemo_list {
	   public static void main(String args[]) {
		   // create vector and array list
		   List arrlist = new ArrayList();
		   Vector v = new Vector();
		      
		   // populate the vector
		   v.add("A");       
		   v.add("B");
		   v.add("C");
		   v.add("D");
		   v.add("E");
		   
		   System.out.println(v);
		      
		   // create enumeration
		   Enumeration e = v.elements();
		   
		   System.out.println(e);
		      
		   // get the list
		   arrlist = Collections.list(e);
		      
		   System.out.println("Value of returned list: "+arrlist);
		   }    

}
