package com.Training.General_Program;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class D {

	public int M()
	{
		return 1;
	}
	
	public int M(int x)
	{
		return x;
	}
	public static void main(String[] args) {
		D d= new D();
		System.out.println(d.M());
		System.out.println(d.M(45));
		String s1 = "abc;xyz;hello";
		String s2=";";
		StringTokenizer st = new StringTokenizer(s1, s2);
		while(st.hasMoreElements())
		{
			System.out.println(st.nextToken());
		}
		
		Calendar c = Calendar.getInstance();
		c.add(Calendar.YEAR, 2);
		c.add(Calendar.DATE, 1);
		Date d1 = c.getTime();
		System.out.println(d1);
		DateFormat df1 = DateFormat.getDateInstance(DateFormat.LONG);
		DateFormat df2 = DateFormat.getDateInstance(DateFormat.FULL);
		System.out.println(df1.format(d1));
		System.out.println(df2.format(d1));
	}

}
