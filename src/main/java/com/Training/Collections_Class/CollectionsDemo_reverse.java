package com.Training.Collections_Class;
import java.util.*;


public class CollectionsDemo_reverse {
	   public static void main(String[] args) {
		   // create array list object
		   ArrayList arrlst = new ArrayList();
		      
		   // populate the list
		   arrlst.add("A");
		   arrlst.add("B");
		   arrlst.add("C");
		   arrlst.add("E");
		   arrlst.add("D");

		   System.out.println("The initial list is :"+arrlst);
		   Collections.sort(arrlst);
		   
		   System.out.println("TAfter sorting :"+arrlst);
		   // reverse the list
		   Collections.reverse(arrlst);
		      
		   System.out.println("The Reverse List is :"+arrlst);
		   }
	}

