package com.Training.Collections_Class;
import java.util.*;

public class CollectionsDemo_nCopies {
	   public static void main(String[] args) {
		   // create a list with n copies 
		   List list = Collections.nCopies(5, "tuitorial Point");
		      
		   // create an iterator
		   Iterator itr = list.iterator();
		      
		   System.out.println("Values are :");
		   while (itr.hasNext()){
		   System.out.println(itr.next());
		   }
		   }  
}
