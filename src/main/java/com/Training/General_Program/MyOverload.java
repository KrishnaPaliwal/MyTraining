package com.Training.General_Program;

import java.util.List;

public class MyOverload {
	
	public void method1(List<String> list)
	{
		
	}

	/*
	public void method1(List<Integer> list)
	{
		
	}	
	*/
	
	public void method1(List<Integer> list1, List<Integer> list)
	{
		
	}
	

	public void method1(String A, String B)
	{
		System.out.println("method1(String A, String B)");
		
	}
	
	public void method1(Integer A, String B)
	{
		
	}
	
	public static void main(String args[])
	{
		MyOverload mv = new MyOverload();
		mv.method1("ff","fdf");	
	}
	
	public static void main(String args[], Integer X)
	{
		MyOverload mv = new MyOverload();
		mv.method1("ff","fdf");

		
	}
	
	public static void myMain(String args[], Integer x)
	{
		MyOverload mv1 = new MyOverload();
		mv1.method1("ff","fdf");
	}
	
	
	public static void myMain(String args[], Integer x, Integer y)
	{
		MyOverload mv1 = new MyOverload();
		mv1.method1("ff","fdf");
	}
	
	public final void myMain(String args[], Integer x, Integer y, Integer t)
	{
		MyOverload mv1 = new MyOverload();
		mv1.method1("ff","fdf");
	}
}
