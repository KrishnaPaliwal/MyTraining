package com.Training.General_Program;

import java.util.LinkedList;
import java.util.List;

public class SwapStringWithoutThirdString {

	public static void main(String [] arg)
	{
		String s1 ="Krishna";
		
		String s2 = "Paliwal";
		LinkedList<String> list = new LinkedList<String>();
		
		s1=s1+s2;
		
		s2=s1.substring(0, (s1.length()-s2.length()));
		System.out.println(s2);
		
		System.out.println(s1.length()+" "+s2.length());
		s1=s1.substring(s2.length(),s1.length());
		
		System.out.println(s1);
		
		if(list instanceof List){
			System.out.println("True");
		}
		
	}
}
