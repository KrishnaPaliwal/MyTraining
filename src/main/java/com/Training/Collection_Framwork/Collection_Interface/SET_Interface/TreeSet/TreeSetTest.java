package com.Training.Collection_Framwork.Collection_Interface.SET_Interface.TreeSet;

import java.util.TreeSet;

// It is must to implement Comparable interface if adding custom objects in TreeSet
public class TreeSetTest {

	public static void main(String [] args) {
		
		TreeSet<String> tset = new TreeSet<>();
		
		tset.add("Krishna");
		tset.add("Paliwa");
		tset.add("Prerita");
		
		for(String s : tset) {
			
			System.out.println("Name::"+s);
		}
		
		
		Book b1 = new Book(100, "Java");
		Book b2 = new Book(200, "C#");
		Book b3 = new Book(300, "Java Scrpit");
		
		TreeSet<Book> btset = new TreeSet<>();
		
		btset.add(b1);
		btset.add(b2);
		btset.add(b3);

		for(Book s : btset) {
			
			System.out.println("Book::"+s);
		}
	}
}
