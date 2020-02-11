package com.Training.General_Program;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.Training.Collection_Framwork.*;
import com.Training.Collection_Framwork.Collection_Interface.LIST_Interface.AbstractList.AbstractSequentialList.*;

class A
{
	public static void staticMethod()
	{
		System.out.println("static method in A");
	}
	
	protected void method()
	{
		System.out.println("Parent method");
	}
	
	void NonInheritatedMethodOfA()
	{
		System.out.println(" in NonInheritatedMethodOfA");
	}
	
}

class B extends A {
	
	@Override
	public void method()
	{
		System.out.println("B method");
	}
	
/*	public static void staticMethod()
	{
		System.out.println("static method in B");
	}*/
	
	void methodB()
	{
		System.out.println("methodB");
	}
	
	public A getA(A a)
	{
		return a;
	}
}

class C extends A {
		
		
		protected void method()
		{
			System.out.println("C method");
			super.method();
		}
		
		
		void methodC()
		{
			System.out.println("methodC");
		}
}		
	
public class ApplicationABC{
	
	@SuppressWarnings("static-access")
	public static void main(String args[])
	{
		String s= "krisha";
		String s2 = s+"Paliwal";
		System.out.println("Paliwal".charAt(2));
		CollectionsDemo d = new CollectionsDemo();
		System.out.println(s2);
		A aa = new A();
		A ab = new B();
		A ac = new C();
		B b = new B();
		C c = new C();
		b.NonInheritatedMethodOfA();
		c.NonInheritatedMethodOfA();
	
		
		aa.method();
		ab.NonInheritatedMethodOfA();
		
		
		ab.method();
		ac.method();
		
		A.staticMethod();
		B.staticMethod();
		ab.method();
		ab.NonInheritatedMethodOfA();
		
		b.staticMethod();
		
/*		public A getA(A a)
		{
			return a;
		}*/
		A aaa = b.getA(aa);
		
		String k1 ="Krishna";
		String k2="Krishna";
		System.out.println(k1==k2);
		System.out.println(k1.equals(k2));

		String k11 =new String("Krishna");
		String k22=new String("Krishna");
		System.out.println(k11==k22);
		System.out.println(k11.equals(k22));	
		
		System.out.println(k1==k22);
		System.out.println(k1.equals(k22));	
		
		
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("name", "Krishna");
		map.put("surname", "paliwal");
		
		
		
		Iterator it = map.values().iterator();
		
		for(String tring:map.values())

			System.out.println(tring);
		
		
		List<String> list = new LinkedList<String>();
		
		list.add("ff");
		list.add("fsa");
		
		Iterator itr= list.iterator();
		while(itr.hasNext()){
			System.out.println(itr.next());
		}
		for(String iif:list)
		{System.out.println(iif);
		
		}
		}
}

		
		
		

