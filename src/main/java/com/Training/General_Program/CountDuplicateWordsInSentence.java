package com.Training.General_Program;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class CountDuplicateWordsInSentence {
	
	public static void main(String []rr)
	{	
		Scanner sc = new Scanner(System.in);
		
		
		String sent = sc.nextLine();
		
		CountDuplicateWordsInSentence.count(sent);
		
	}

	public static Integer count(String string)
	{
		String [] st = string.split(" ");
		Set<String> set = new HashSet<String>();
		
		List list = Arrays.asList(st);
		for(int i=0; i<st.length; i++)
		{
			
			if(!set.add(st[i])){
				System.out.println(st[i]+" is duplicate");
				System.out.println(Collections.frequency(list, st[i]));
			}
			
			
			
		}
		
		return 0;
	}
	
}
