package com.Training.Comparator_and_Comparable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class SortString {

	public static void main(String... args)
	{
		List<String> list = new ArrayList<String>();
		list.add("java");
		list.add("jee");
		list.add("spring");
		list.add("hibernate");
		
		Collections.sort(list);
		
		for (String string : list) {
			System.out.println(string);
		}
	}
}
