package com.myTraining.General_Program;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class FindDuplicateInArray {
	
	public static void main(String [] args)
	{
		Scanner sc = new Scanner(System.in);
		
		System.out.println("provide the lenght of array");
		int n = sc.nextInt();
		
		int array [] = new int [n]; 
		
		System.out.println("Enter "+ n+ " elements");
		for(int i=0 ; i<n; i++)
		{
			array[i] = sc.nextInt();
		}
		
		for(int i : array)
		{
		System.out.println(i);
		}
		
		Set<Integer>  set = new HashSet<>();
		for(int i=0 ; i<n; i++)
		{	
			if(!set.add(array[i]))
			{
				System.out.println("Duplicate value is " + array[i]);
			}

		}
	}

}
