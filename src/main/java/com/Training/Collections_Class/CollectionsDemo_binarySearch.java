package com.myTraining.Collections_Class;

import java.util.*;

public class CollectionsDemo_binarySearch {
   public static void main(String args[]) {
   // create arraylist       
   List arlst=new ArrayList();
   
   List arlst2=new LinkedList();
   arlst2.add(334443);
   arlst2.add("Kfriddscs");
   arlst2.add("QUALITY");
      
   // populate the list
   arlst.add("TP");
   arlst.add("PROVIDES");
   arlst.add("QUALITY");
   arlst.add("TUTORIALS");
   arlst.add(56);
   arlst.add(arlst2);
   System.out.println(arlst);
   System.out.println(arlst2);
   System.out.println(arlst2.get(1));
   
      
   // search the list for key 'QUALITY'
   int index=Collections.binarySearch(arlst, "QUALITY");     
      
   System.out.println("'QUALITY' is available at index: "+index);
   }    
}
;