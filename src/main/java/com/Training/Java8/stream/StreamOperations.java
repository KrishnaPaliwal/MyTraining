package com.Training.Java8.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamOperations {

	public static void main(String args[]) {
		
		// Count elements in list
		List<String> list = Arrays.asList("Krishna", "Ajay", "Mukund");
		long count1 = list.stream().count();
		
		long count2 = Stream.of("Krishna", "Ajay", "Mukund").count();
		
		System.out.println(count1);
		System.out.println(count2);
		
		//Sort the list and find first element
		list.stream().sorted().findFirst().ifPresent(System.out::println);
		
		// Fliter list and print which starts with specific word
		list.stream().filter(name -> name.startsWith("Mu")).forEach(name -> System.out.println(name));
		//OR
		Stream.of("Krishna", "Ajay", "Mukund").filter(name -> name.startsWith("Mu")).forEach(name -> System.out.println(name));
		
		
		  List<String> nameList = list.stream().map(name -> name.toUpperCase()).collect(Collectors.toList());
		  System.out.println(nameList);
		  //OR 
		  List<String> collected = Stream.of("Krishna", "Ajay", "Mukund") .map(name ->name.toUpperCase()) .collect(Collectors.toList());
		 
		  System.out.println(collected);
		
		  // Find average
		  Arrays.stream(new int[] {1, 2, 3, 4,}).map(n -> n*n).average().ifPresent(System.out::println);
		
	}
	
	
}
