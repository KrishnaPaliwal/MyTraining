package com.Training.GENERICS;

import java.util.ArrayList;
import java.util.List;

// refer http://tutorials.jenkov.com/java-generics/wildcards.html

public class WildCardExtendsExample {
	
	public static void processElements(List<? extends A> elements){
		   for(A a : elements){
		      System.out.println(a);
		   }
		}
	
	public static void insertElements(List<? super A> list){
	    list.add(new A());
	    list.add(new B());
	    list.add(new C());
	}
	
	public static void main(String [] arg)
	{
		
		List<A> listA = new ArrayList<A>();
		insertElements(listA);
		processElements(listA);

		List<B> listB = new ArrayList<B>();
	
		processElements(listB);

		List<C> listC = new ArrayList<C>();

		processElements(listC);
	}
}

class A
{

}

class B extends A {
	
}

class C extends A
{
	
}