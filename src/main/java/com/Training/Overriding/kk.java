package com.myTraining.Overriding;

public class kk {
	public static String  s;
	
public static void main(String ddd[])
{
	kk o = new kk();
	o.s= "cscs";
	o.s.charAt(2);
	
	System.out.println(length(s));
	Integer i = 4;
	String f = String.valueOf(i);
	
	//Integer.valueOf(f);
	Long.valueOf(i);
	
	//System.out.println(i.toString());
	System.out.println(String.valueOf(i));
	
	
	
}

public static int length(String s)
{
	return s.length();
}
}
