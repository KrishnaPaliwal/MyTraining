package com.myTraining.Enumeration_Interface;

import java.util.Enumeration;
import java.util.Vector;

public class EnumerationTester {

	public static void main(String[] args)
	{
		
		Vector<String> dayNames = new Vector<String>();
		dayNames.add("Monday");
		dayNames.add("Tuesday");
		dayNames.add("Wednesday");
		dayNames.add("Thursday");
		Enumeration<String> days = dayNames.elements();
 		while(days.hasMoreElements())
		{
			System.out.println(days.nextElement());
		}
	}
}
