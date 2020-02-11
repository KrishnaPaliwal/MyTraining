package com.Training.Collection_Framwork.Collection_Interface.LIST_Interface.AbstractList.Vector;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;
import java.util.Stack;

public class VectorDemo 
{

	public static void main(String... args)
	{
		Vector<Integer> v = new Vector<Integer>(3,2);
		System.out.println("Intial size of Vector is\t"+v.size());
		System.out.println("Intial capacity of Vector is\t"+v.capacity());
	
		v.add(new Integer(1));
		v.add(new Integer(2));
		v.add(new Integer(3));
		v.add(new Integer(4));
		System.out.println("now size of Vector is\t"+v.size());
		System.out.println(" now capacity of Vector is\t"+v.capacity());
	
		v.add(new Integer(5));
		System.out.println("now size of Vector is\t"+v.size());
		System.out.println(" now capacity of Vector is\t"+v.capacity());	
	
		v.add(new Integer(6));
		System.out.println("now size of Vector is\t"+v.size());
		System.out.println(" now capacity of Vector is\t"+v.capacity());	
		
		Enumeration<Integer> E1 = v.elements();
		while(E1.hasMoreElements())
		{
			System.out.println(E1.nextElement()+" Using Vector.elements()");
		}
		
		Enumeration<Integer> E2 = Collections.enumeration(v);
		while(E2.hasMoreElements())
		{
			System.out.println(E2.nextElement()+" Using Collections.enumeration()");
		}
		
		Iterator<Integer> I1 = v.iterator();
		
		while(I1.hasNext())
		{
			System.out.println(I1.next()+" Using Vector.iterator()");
		}
	}
}

/*Intial size of Vector is	0
Intial capacity of Vector is	3
now size of Vector is	4
 now capacity of Vector is	5
now size of Vector is	5
 now capacity of Vector is	5
now size of Vector is	6
 now capacity of Vector is	7
*/