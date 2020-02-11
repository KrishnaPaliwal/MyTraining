package com.Training.Collections_Class;
import java.util.*;

public class CollectionsDemo_synchronizedList {
	   public static void main(String[] args) {
		   // create vector object 
		   List<String> list = new ArrayList<String>();
		      
		   // populate the list
		   list.add("1");
		   list.add("2");
		   list.add("3");
		   list.add("4");
		   list.add("5");
		      
		   // create a synchronized list
		   List<String> synlist = Collections.synchronizedList(list);
		     
		   System.out.println("Sunchronized list is :"+synlist);
		   }

}
