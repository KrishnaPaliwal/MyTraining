package com.Training.Enhanced_For_Loop;

public class EnhancedForLoop {
	
	public static void main(String args [])
	{
		int[] IntArray = {1,2,3,4,5,6};
		
		// Enhanced for loop
		for(int printInt : IntArray)
		{
			System.out.println(printInt);	
		}
	
		char[] CharArray = {'A','B','C',};
		
		for(char printChar : CharArray)
		{
			System.out.println(printChar);
		}
		
		String[] StringArray = {"Krishna","Kumar","Paliwal",};
		
		for(String printString : StringArray)
		{
			System.out.println(printString);
		}
	
		int [] intarray = new int[5];
		intarray[0] = 8;
		intarray[1] = 7;
		for(int i : intarray )
		{
			System.out.println(i);
		}
		
	}
	
}
