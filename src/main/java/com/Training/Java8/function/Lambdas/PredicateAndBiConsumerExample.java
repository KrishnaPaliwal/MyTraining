package com.learn.lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

public class PredicateAndBiConsumerExample {

	public static void main(String [] args) {
		
		List<Instructor> instructorList = new ArrayList<>();
		
		Instructor l1 = new Instructor("Krishna", Arrays.asList("Java", "Golang", "Python"), true, 10);
		Instructor l2 = new Instructor("Durga", Arrays.asList("Hindi", "Sahitya", "12th"), true, 4);
		Instructor l3 = new Instructor("Prerita", Arrays.asList("SS", "English", "Grammer"), true, 1);
		
		instructorList.addAll(Arrays.asList(l1,l2,l3));
		
		for(Instructor instructor : instructorList ) {
			if(instructor.isOnlineCourse() && instructor.yearsOfExperience>3) {
				System.out.println(instructor.getName()+" "+instructor.getCourses() );
			}
		}
		
		Predicate<Instructor> p1 = (i) -> i.isOnlineCourse()==true;
		Predicate<Instructor> p2 = (i) -> i.yearsOfExperience>3;
		
		BiConsumer<String, List<String>> b1 = (name, courses) -> System.out.println(name+""+ courses);
		
		instructorList.forEach( instructor -> {
			if(p1.and(p2).test(instructor)) {
				b1.accept(instructor.getName(), instructor.getCourses());
				
			}
		}
				);
		
		
		
		
		
	}
}
