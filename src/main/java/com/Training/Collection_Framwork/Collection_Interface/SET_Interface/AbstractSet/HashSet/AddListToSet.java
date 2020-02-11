package com.Training.Collection_Framwork.Collection_Interface.SET_Interface.AbstractSet.HashSet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AddListToSet {
	
	public static void main(String [] args)
	{
		List<String> list  = new ArrayList<String>();

		list.add("one");
		list.add("two");
		list.add("one");
		list.add("three");
		list.add("one");
		list.add("three");
		list.add("one");
		list.add("three");
		
		list.add("one");
		list.add("two");
		list.add("one");
		list.add("three");
		list.add("one");
		list.add("three");
		list.add("one");
		list.add("three");
		
		list.add("one");
		list.add("two");
		list.add("one");
		list.add("three");
		list.add("one");
		list.add("three");
		list.add("one");
		list.add("three");
		
		list.add("one");
		list.add("two");
		list.add("one");
		list.add("three");
		list.add("one");
		list.add("three");
		list.add("one");
		list.add("three");
		
		list.add("one");
		list.add("two");
		list.add("one");
		list.add("three");
		list.add("one");
		list.add("three");
		list.add("one");
		list.add("three");
		
		list.add("one");
		list.add("two");
		list.add("one");
		list.add("three");
		list.add("one");
		list.add("three");
		list.add("one");
		list.add("three");
		
		Set<String> set = new HashSet<String>(list);
		
		for(String st: set)
		{
			System.out.println(st);
		}
		
		
	}
}