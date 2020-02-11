package com.Training.General_Program;

import java.util.Arrays;

public class MissingNmberFinder {
	
	public static int missingNumber(int [] a)
	{
		Arrays.sort(a);
		
		if(a==null || a.length<=0 )
		{
			throw new IllegalArgumentException("Array is null or empty");
		}
		
		int left=0;
		
		int right= a.length-1;
		
		while(left<=right)
		{
			int middle = (left+right)>>1;
			 if(a[middle]!=middle)
			 {
				 if (middle==0 || a[middle-1]==middle-1)
				 {
					 return middle;
				 }
				 
				 right = middle-1;
			 }
			 
			 else 
			 { 
				 left = middle+1;
			 }
			 
		}
		throw new RuntimeException("No missing Numbers");
	}

	public static void main(String [] args)
	{
		int [] array = new int[] {1,2,3,5,6};
		
		System.out.println("missing number is "+ missingNumber(array));
		
	}
	
}
