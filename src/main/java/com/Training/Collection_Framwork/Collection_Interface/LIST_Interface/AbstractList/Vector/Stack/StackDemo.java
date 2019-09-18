package com.myTraining.Collection_Framwork.Collection_Interface.LIST_Interface.AbstractList.Vector.Stack;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Stack;
import java.util.Vector;

public class StackDemo
{

	public Stack<String> myshowpush(Vector<String> st, String s)
	{
		//push method
		((Stack<String>) st).push(s);
		return (Stack<String>) st;
		
	}
	public static void main(String args[])
	{
		StackDemo StDemo = new StackDemo();
		Vector<String> Stk = new Stack<String>();
		
		//Down Casting since Stk is Vector type and can not access method of child class Stack
		((Stack<String>) Stk).push("wedf");
		
		StDemo.myshowpush(Stk, "Paliwal");
		System.out.println(Stk);
		StDemo.myshowpush(Stk,"Krishna");
		System.out.println(Stk);
		
		//pop method
		//Down Casting since Stk is Vector type and can not access method of child class Stack
		((Stack<String>) Stk).pop();
		System.out.println(Stk);
		Stk.add("ffffffffffffffffff");
		
		String s = ((Stack<String>) Stk).peek();
		System.out.println(s);
		
		Enumeration<String> E1 = Stk.elements();
		
		
		while(E1.hasMoreElements())
		{
			System.out.println(E1.nextElement());
		}
		
		Iterator<String> I1 = Stk.iterator();
		
		while(I1.hasNext())
		{
			System.out.println(I1.next());
		}
	}
}
