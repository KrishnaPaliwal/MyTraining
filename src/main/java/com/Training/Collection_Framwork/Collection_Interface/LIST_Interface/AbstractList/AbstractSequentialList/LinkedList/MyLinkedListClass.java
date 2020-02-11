package com.Training.Collection_Framwork.Collection_Interface.LIST_Interface.AbstractList.AbstractSequentialList.LinkedList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class MyLinkedListClass {


	public static void main(String... agrs)
	{
		final List<String> LLst = new LinkedList<String>();

		LLst.sort(null);
		final List<Integer> LLstw = new ArrayList<>();
		LLstw.add(3);
		LLstw.add(2);
		LLstw.add(1);
		LLstw.sort(null);
		System.out.println(LLst.getClass());
		
		LLst.add("Krishna");
		LLst.add("Kumar");
		LLst.add("Paliwal");
		LLst.add(2,"Mandaur");
		LLst.add(2,"MP");
		LLst.add(5,"Madhya Pradesh");
		System.out.println(LLst);
		System.out.println("Sorted");
		Collections.sort(LLst);
		System.out.println(LLst);
		
		System.out.println(Collections.binarySearch(LLst, "Madhya Pradesh"));
		
// set method
		LLst.set(1,"Durga");
		System.out.println(LLst);
		
//remove method
		LLst.remove(5);
		System.out.println(LLst);
	
//contains method
		System.out.println(LLst.contains("Krishna")); 
		
		List<String> newLLst = new LinkedList<String>();
		newLLst.add("Durga");
		newLLst.add("Paliwal");		
		LLst.addAll(newLLst);
		
		System.out.println(LLst);
		
		Iterator I = LLst.iterator();
		
		while(I.hasNext())
		{
		System.out.println(I.next());	
		}
		
	}
	
}