package com.myTraining.Collections_Class;

import java.util.*;

public class CollectionsDemo_addAll {
   public static void main(String args[]) {
   // create array list object       
   List arrlist = new ArrayList();
      
   // populate the list
   arrlist.add("A");
   arrlist.add("B");
   arrlist.add("C");  
      
   System.out.println("Initial collection value: "+arrlist);
      
   // add values to this collection
   @SuppressWarnings("unchecked")
boolean b = Collections.addAll(arrlist, "1","2","3");
      
   System.out.println("Final collection value: "+arrlist);
   }    
}
