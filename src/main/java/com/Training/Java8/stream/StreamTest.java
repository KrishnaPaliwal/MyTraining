package com.Training.Java8.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StreamTest {

	public static void main(String args []) {
		
		Book b1 = new Book("title1", "Krishna", "Paliwal", 300);
		
		Book b2 = new Book("title2", "Akash", "Sinha", 400);
		
		Book b3 = new Book("title3", "Ramesh", "Sharma", 600);
		
		
		List<Book> books = Arrays.asList(b1, b2, b3); 
		
		int allPages = books.stream().collect(Collectors.summingInt(Book::getNoOfPages));
		
		int allPPages = books.parallelStream().collect(Collectors.summingInt(Book::getNoOfPages));
		
		System.out.println("AllPages: "+allPages);
		System.out.println("allPPages: "+allPPages);
	}
}
