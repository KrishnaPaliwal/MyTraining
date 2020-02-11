package com.Training.General_Program;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

//Reverse of String
public class MyTesting {
	
	public static void main(String [] args)
	{
		String s = new String("Krishna");
		
		System.out.println(s.length());
		char[] ch = new char[s.length()];
		for(int i=0; i<(s.length()); i++)
		{
			ch[i] = s.charAt(i);
		}
		for(int i=0; i<(s.length()); i++)
		{
			if (ch[i] == (s.charAt((s.length()-1)-i)))
			{
				System.out.println(s.charAt((s.length()-1)-i));
				System.out.println(ch[i]);
				
				System.out.println("String is palindrom");
			}
			else {
				System.out.println("String is not palindrom");
			}
		}
	
		
	}
}