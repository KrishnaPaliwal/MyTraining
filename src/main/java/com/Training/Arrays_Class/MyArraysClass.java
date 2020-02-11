package com.Training.Arrays_Class;

import java.lang.reflect.Array;
import java.util.concurrent.DelayQueue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unused")
public class MyArraysClass {
		
	public static void main(String [] args)
	{
		char [] crarr1 = new char[5];
		crarr1[0]= 'X';
		crarr1[1]= 'T';
		crarr1[2]= 'Y';
		crarr1[3]= 'A';
		crarr1[4]= 'E';
		
		//Before Sorting
		for (char x : crarr1)
		{
			System.out.println(x);
		}

	
//sort method based on Dual-Pivot Quicksort Algorithm to sort Array
		Arrays.sort(crarr1);
		
		//After Sorting
		for (char x : crarr1)
		{
			System.out.println(x);
		}
		
		String [] strarr = new String []{"Yarry", "Poe", "Curly", "Laprz"};
		Arrays.sort(strarr);
		for (String x : strarr)
		{
			System.out.println(x);
		}
		
		
//copyOf method	
		char [] crarr2 = Arrays.copyOf(crarr1, 9);
		System.out.println(crarr2);
	
//copyOfRange method	
		char [] crarr3 = Arrays.copyOfRange(crarr1, 2, 4);
		System.out.println(crarr3); //
	
		
//asList method
	// 1st way
		List<String> list1  = Arrays.asList("Larry", "Moe", "Curly");
		
	
	// 2nd way
		List<String> list2  = new ArrayList<String>(Arrays.asList(new String []{"Larry", "Moe", "Curly"}));
		
		list2.add("Jonney");
		System.out.println(list2.get(3));
		if (list2.contains("Larry")) System.out.println("true");
	
	// 3rd way
		List<String> list3  = new ArrayList<String>(Arrays.asList("Larry", "Moe", "Curly"));
		
		Set<String> set = new HashSet<>(list3);
//binarySearch method 
/*
binarySearch method Returns index of the search key, if it is contained in the array; else
otherwise, (-(insertion point) - 1). The insertion point is defined as the point at which the 
key would be inserted into the array.Note that this guarantees that the return value
will be >= 0 if and only if the key is found.
*/		
		System.out.println(Arrays.binarySearch(strarr,"Zazrz")); //output : -5 (Not found but can be inserted at position 5)
		
		System.out.println(Arrays.binarySearch(strarr,1,2,"Zazrz")); //output :-3 (Not found but can be inserted at position 3)
		
		System.out.println(Arrays.binarySearch(strarr, "Laprz")); //output :1

//deepHashCode method : to compare 'nested Array' ie elements of array itself are arrays ie 2D array
/*
 It's better to use Arrays.equals() to compare non nested Array and Arrays.deepEquals() to compare nested Array		
 */
		System.out.print(Arrays.deepHashCode(strarr));
		
		
	}

	
}
