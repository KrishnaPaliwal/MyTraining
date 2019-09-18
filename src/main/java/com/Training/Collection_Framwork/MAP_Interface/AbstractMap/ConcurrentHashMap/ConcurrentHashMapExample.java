package com.myTraining.Collection_Framwork.MAP_Interface.AbstractMap.ConcurrentHashMap;

import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Hashtable;
import java.util.Map.Entry;


public class ConcurrentHashMapExample
{
    
	public void someFunction() throws Exception {
	    try {
	       throw new Exception("gdfg");
	    } finally {
	        System.out.println("finally");
	    }
	}
	
    public static void main(String[] args)
    {
    	ConcurrentHashMapExample fdsdf = new ConcurrentHashMapExample();
    	try {
			fdsdf.someFunction();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	List<Integer> list  = new ArrayList<>();
    	list.add(1);
    	list.add(2);
    	System.out.println(list.size());
    	System.out.println();
    	list.set(1, 4);
    	System.out.println(list);
			/*
        Map<String,String> premiumPhone = new ConcurrentHashMap<String,String>();
        Collections.synchronizedMap(premiumPhone);
        premiumPhone.put("Apple", "iPhone6");
        premiumPhone.put("HTC", "HTC one");
        premiumPhone.put("Samsung","S6");
        // below commented line of code is wrong because ConcurrentHashMap
        // does NOT allow null key or null value
        
       // premiumPhone.put(null, "dfsf");
        //premiumPhone.put("fesf", null);
        
        Iterator iterator = premiumPhone.keySet().iterator();
        
        while (iterator.hasNext())
        {synchronized(premiumPhone){
            System.out.println(premiumPhone.get(iterator.next()));
           premiumPhone.put("Sony", "Xperia Z");
        }
    }
        for(Map.Entry<String, String> entry : premiumPhone.entrySet())
        {
        	System.out.println(entry.getKey());
        	//premiumPhone.put("NOKIA", "3333");
        }
        
        System.out.println(premiumPhone);
    */}
    
}
