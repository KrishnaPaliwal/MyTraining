package com.myTraining.Collections_Class;
import java.util.*;


public class CollectiosDemo_unmodifiableList {

	   public static void main(String[] args) {
		   // create array list
		   List<Character> list = new ArrayList<Character>();
		      
		   // populate the list
		   list.add('X');
		   list.add('Y');
		      
		   System.out.println("Initial list: "+ list);
		      
		   // make the list unmodifiable
		   List<Character> immutablelist = Collections.unmodifiableList(list);
		      
		   // try to modify the list
		   immutablelist.add('Z');      
		   }

}
