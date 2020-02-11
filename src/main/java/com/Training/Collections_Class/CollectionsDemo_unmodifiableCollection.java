package com.Training.Collections_Class;
import java.util.*;

public class CollectionsDemo_unmodifiableCollection {
	   public static void main(String[] args) {
		   // create array list
		   Vector<Character> vec = new Vector<Character>();
		      
		   // populate the list
		   vec.add('X');
		   vec.add('Y');
		      
		   System.out.println("Initial list: "+ vec);
		      
		   Collection<Character> immutableVector = Collections.unmodifiableCollection(vec);
		      
		   // try to modify the list
		   immutableVector.add('Z');      
		   }

}
