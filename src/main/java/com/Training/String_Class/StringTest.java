package com.Training.String_Class;

public class StringTest {
	public static void main(String [] args)
	{
		String s1="PALIWAL";
		//String s2="PALIWAL";
		System.out.println(s1.hashCode());
		String s2 = new String("PALIWAL");
		System.out.println(s2.hashCode());
		System.out.println(s1);
		System.out.println(s2);		
		System.out.println(s1.equals(s2));
		System.out.println(s1==s2);
		s1=s1+"XYZ";
		s2=s2+"XYA";
		System.out.println(s1.hashCode());
		System.out.println(s2.hashCode());
		System.out.println(s1);
		
		
		
		MyStringClass obj1 = new MyStringClass();
		MyStringClass obj2 = new MyStringClass();
		
		System.out.println(obj1.equals(obj2));
		System.out.println(obj1.hashCode());
		System.out.println(obj2.hashCode());
		System.out.println(obj2 instanceof MyStringClass); 
		

		
	}

}
