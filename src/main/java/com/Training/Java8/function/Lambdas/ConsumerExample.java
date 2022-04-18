package com.learn.lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class ConsumerExample {

	public static void main(String dd[]) {
		
		List<Instructor> nameList = new ArrayList<>();
		
		Instructor l1 = new Instructor("Krishna", Arrays.asList("Java", "Golang", "Python"));
		Instructor l2 = new Instructor("Durga", Arrays.asList("Hindi", "Sahitya", "12th"));

		nameList.addAll(Arrays.asList(l1,l2));
		
		Consumer<Instructor> c1 = (input) ->  {System.out.println(input.getName());};
		Consumer<Instructor> c2 = (input) ->  {System.out.println(input.getCourses());};

		nameList.forEach(c1.andThen(c2));
	}
	
}

