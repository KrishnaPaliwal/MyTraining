package com.myTraining.Key_Words.Switch_keyword;

import java.util.List;
import java.util.Set;

public class SwitchClass {
	    
	public static void main(String args[])
	{
		int month_days[];
		month_days = new int[12];
		
	//int x = Integer.parseInt(args[0]);
		int x=2;
		
		switch(x)
		{
			case 1:
				System.out.println("This is 1");
			break;
			
			case 2: 
				System.out.println("This is 2");
				
				default: System.out.println("This is unknown");
		}
	}
}
