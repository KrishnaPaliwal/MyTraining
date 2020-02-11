package com.Training.Collection_Framwork.MAP_Interface;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class MapDemo {
	public static void main(String[] args) {
	
	//TreeMap Example 
		Map<String, String> map = new TreeMap<String, String>();
		 map.put("name","TreeMap");
		 map.put("Details","Tree Map Example");
		 map.put("Details","Tree Mapjhjhj Example");
		 System.out.println(map.get("name")); //TreeMap
		 System.out.println(map); //{Details=Tree Map Example, name=TreeMap}
		 System.out.println(map.keySet()); //[Details, name]
		 System.out.println(map.values()); //[Tree Map Example, TreeMap]
		 
//-------------------------------------------------------------------------------------	 
	//LinkedHashMap Example by instantiating object of LinkedHashMap by LinkedHashMap map class.
		 LinkedHashMap<String, String> lhmap = new LinkedHashMap<String, String>(); 
		 lhmap.put("name","LinkedHashMap");
		 lhmap.put("Details", "LinkedHashMap Example");
		 lhmap.put(null, null);
		 lhmap.put("Null 1", null);
		 System.out.println(lhmap.get("name"));
		 System.out.println(lhmap);
		 System.out.println(lhmap.keySet());
		 System.out.println(lhmap.values());
		 
	//LinkedHashMap Example by instantiating object of Map interface by LinkedHashMap map class.
	// This is better way to instantiate the object, since if you object of MAP interface is 
	//declared then we can instantiate by any decendent class(TreeMap, HashMap, LinkedHashMap )
		 map = new LinkedHashMap<String, String>();
		 
		 map.put("name","LinkedHashMap");
		 map.put("Details", "LinkedHashMap Example through MAP object");
		 System.out.println(map.get("name"));
		 System.out.println(map);
		 System.out.println(map.keySet());
		 System.out.println(map.values());
	
//-----------------------------------------------------------------------
	//HashMap Example .
		 HashMap<String, String> hmap = new HashMap<String, String>();
		 hmap.put("name","HashMap---------------------kriississs----------------------------------");
		 hmap.put("Details", "HashMap Example");
		 hmap.put(null, null);
		 hmap.put("Null 1", null);
		 System.out.println(hmap.get("name"));
		 System.out.println(hmap);
		 System.out.println(hmap.keySet());
		 System.out.println(hmap.values());
		 
	//HashMap Example by instantiating object of Map interface by HashMap map class.
		 map = new HashMap<String, String>();
		 map.put("name","HashMap");
		 map.put("Details", "HashMap Example");
		 System.out.println(map.get("name"));
		 System.out.println(map);
		 System.out.println(map.keySet());
		 System.out.println(map.values());
 //-------------------------------------------------------------------
	//SortedMap Example .
		 
		 SortedMap<String, String> smap = new TreeMap<String, String>();
		 smap.put("name","TreeMap");
		 smap.put("Details", "TreeMap Example");
		 System.out.println(smap.get("name"));
		 System.out.println(smap);
		 System.out.println(smap.keySet());
		 System.out.println(smap.values());
	
//-------------------------------------------------------------------
	//Hashtable Example .		 
		 
		 Hashtable<String, String> ht = new Hashtable<String, String>();

		 ht.put("name", "HashTable");
		 ht.put("Details", "HashTable Example");
		// ht.put(null, "ttrtr");
		 System.out.println(ht);
		 System.out.println(ht.get("name"));
		 
	}
}
