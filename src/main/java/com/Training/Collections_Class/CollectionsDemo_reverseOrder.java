package com.myTraining.Collections_Class;
import java.util.*;
public class CollectionsDemo_reverseOrder {

	public static void main(String args[]) {  
		   // create linked list object  	   
		   LinkedList<Integer> list = new LinkedList<Integer>();  
		      
		   // populate the list 
		   list.add(-28);  
		   list.add(20);  
		   list.add(-12);  
		   list.add(8);  
		   
		   Collections.sort(list);
		   System.out.println(list);
		   
		   // create comparator for reverse order
		   Comparator cmp = Collections.reverseOrder();  

		   // sort the list
		   Collections.sort(list, cmp);  
		   
		   System.out.println(list);
//				  
//		   System.out.println("List sorted in ReverseOrder: ");      
	   for(Integer i : list){
	   System.out.println(i+ " ");
		   }	
	}
}

