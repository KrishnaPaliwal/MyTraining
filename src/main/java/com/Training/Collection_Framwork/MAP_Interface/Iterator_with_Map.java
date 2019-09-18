package com.myTraining.Collection_Framwork.MAP_Interface;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;


public class Iterator_with_Map {
	public static void main(String args[])
	{
		Map<String,String> map = new LinkedHashMap<String,String>();
		
		map.put("Name","Krishna");
		map.put("City","Mandsaur");
		map.put("Education","BE");
		map.put("Profession","Software Engineer");
		System.out.println(map);
	
		// Iterate through map for keys of map
		Iterator<?> itk = map.keySet().iterator();
		while(itk.hasNext())
		{
			System.out.println(itk.next());
		}
		
		// Iterate through map for values of map
		Iterator<?> itv = map.values().iterator();
		while(itv.hasNext())
		{
			System.out.println(itv.next());
		}
	}
	

}
