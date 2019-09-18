package com.myTraining.General_Program;

import java.util.Scanner;

public class MyClass {
public static void main(String[] args) {
	Scanner sc = new Scanner(System.in);
	String word8 = sc.next();
	String word9 = sc.next();
	int occurences = word8.indexOf(word9);
	System.out.println(occurences);
	}
}