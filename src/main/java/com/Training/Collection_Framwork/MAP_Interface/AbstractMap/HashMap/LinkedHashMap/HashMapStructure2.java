package com.myTraining.Collection_Framwork.MAP_Interface.AbstractMap.HashMap.LinkedHashMap;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class HashMapStructure2 {
	
	public static void main(String [] args)
	{
	
		Map<Country2, String> myMap2 = new HashMap<Country2, String>();
		Map<String, String> myMap = new LinkedHashMap<String, String>();
		
		Country2 india = new Country2("India", "1000");
		Country2 china = new Country2("China", "23234324");
		Country2 france = new Country2("France", "24434");
		Country2 russia = new Country2("Russia", "343343");
		
		myMap.put("india", "Delhi");
		myMap.put("china", "Beijing");
		myMap.put("france", "Paris");
		myMap.put("russia", "Mowcow");
		myMap.put("india", "Delhi3");
		myMap.put("china", "Beijin4g");
		myMap.put("france", "Pari6s");
		myMap.put(null, null);
		myMap.put("dsdcv", null);
		myMap.put("vfgdvdx", null);

		Iterator<String> it = myMap.keySet().iterator();
		
		while(it.hasNext()){
			
			String country2 = it.next();
			
			String Capital = myMap.get(country2);
			
			System.out.println( Capital);		
		}
			
	}
	
}
