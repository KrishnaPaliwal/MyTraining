package com.myTraining.Collection_Framwork.Collection_Interface.SET_Interface.AbstractSet.HashSet.LinkedHashSet;

import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;

public class MyLinkedHashSetClass {
	
	public static void main(String... args)
	{
		LinkedHashSet<String> lhs = new SortedSet();
		
		lhs.add("Orange");
		lhs.add("Mango");
		lhs.add("Papaya");
		
		Enumeration e1 = Collections.enumeration(lhs);
		
		while (e1.hasMoreElements())
		{
			System.out.println(e1.nextElement());
		}
		
	}

}
