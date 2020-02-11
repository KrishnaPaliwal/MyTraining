package com.Training.Collection_Framwork.MAP_Interface.HashTable;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

@SuppressWarnings("unused")
public class MyHashtableClass {
	
	public static void main(String... args)
	{
		Hashtable<String, String> st = new Hashtable<String,String>();
		st.put("Name", "Krishna");
		st.put("Middle name","Kumar");
		st.put("Surname", "Paliwal");		
		
		Enumeration<String> E1 = st.elements();
		Enumeration<String> E2 = st.keys();
		
		//elements method , Overrides elements() in Dictionary 
		while(E1.hasMoreElements())
		{
			System.out.println(E1.nextElement());
		}
		
		// keys method, Overrides: keys() in Dictionary

		while(E2.hasMoreElements())
		{
			System.out.println(E2.nextElement());	
		}
		
		Iterator<String> I1 = st.keySet().iterator();
		Iterator<?> I2 = st.entrySet().iterator();
		Iterator<Entry<String,String>> I3 = st.entrySet().iterator();
		Iterator<String> I4 = st.values().iterator();
	//-------------------------------------------------------------------------	
	   //Retrieving key from value in hashtable
	       String key= null;
	       String value="Krishna";
	       
	       for(Map.Entry<String,String> entry: st.entrySet())
	        {
	            if(value.equals(entry.getValue()))
	            {
	                key = (String) entry.getKey();
	                break; //breaking because its one to one map
	            }
	        }
	        
	        if (key!=null) System.out.println(key+" fefedf ");
	        
//---------------------------------------------------------------------------------------		
		//keySet
		while(I1.hasNext())
		{
			System.out.println(I1.next());
		}
		
		//entrySet
		while(I2.hasNext())
		{
			System.out.println(I2.next());
		}
		
		//entrySet
		while(I3.hasNext())
		{
			System.out.println(I3.next());
		}
		
		//values
		while(I4.hasNext())
		{
			System.out.println(I4.next());
		}
	}

}
