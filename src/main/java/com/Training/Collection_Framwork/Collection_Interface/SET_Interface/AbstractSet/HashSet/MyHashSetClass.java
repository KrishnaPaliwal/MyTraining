package com.myTraining.Collection_Framwork.Collection_Interface.SET_Interface.AbstractSet.HashSet;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;

public class MyHashSetClass {
	public static void main(String args[])
	{
		HashSet<String> Hset = new HashSet<String>();
		
		Hset.add("Mandsuar");
		Hset.add("BHOPAL");
		Hset.add("Bangalore");

		String obj = Collections.max(Hset);
		   System.out.println(obj);
		   
		Enumeration<String> E1 = Collections.enumeration(Hset);
		while (E1.hasMoreElements())
		{
			System.out.println(E1.nextElement());
		}
	
 	}

}
