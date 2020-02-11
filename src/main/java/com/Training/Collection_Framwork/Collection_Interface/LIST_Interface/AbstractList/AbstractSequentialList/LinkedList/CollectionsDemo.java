package com.Training.Collection_Framwork.Collection_Interface.LIST_Interface.AbstractList.AbstractSequentialList.LinkedList;

import java.util.*;

public class CollectionsDemo {
   public static void main(String args[]) {
   // create array list object       
   List arrlist = new LinkedList();
      
   // populate the list
   arrlist.add("A");
   arrlist.add("B");
   arrlist.add("C");  
   System.out.println("before updateList method " + arrlist);  
   
   CollectionsDemo obj = new CollectionsDemo();
   obj.updateList(arrlist);
   
   System.out.println("after updateList method " + arrlist); 
   
   System.out.println("Initial collection value: "+arrlist);
      
   // add values to this collection
   boolean b = Collections.addAll(arrlist, "1","2","3");
      
   System.out.println("Final collection value: "+arrlist);
   }    
   
   public List updateList(List list)
   {
	   list.add("D");
	   System.out.println("in updateList method " + list);
	   return list;
   }
}
