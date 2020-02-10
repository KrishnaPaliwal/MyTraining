package com.Training.Java8.stream;

import java.util.stream.*;
public class PrimitiveStreamClass {

	public static void main(String[] arg) {
		
		IntStream.range(1, 4).forEach(num -> System.out.println(num));
		//OR
		IntStream.range(1, 4).forEach(System.out::println);
		
		
		Stream.of(1.2, 3.5, 5.6).mapToInt(num -> new Double(num).intValue()).forEach(System.out::println);
		//OR
		Stream.of(10.2, 20.5, 30.6).mapToInt(Double::intValue).forEach(System.out::println);
	}
}
