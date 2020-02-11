package com.Training.Constructors;

public class constructorExample {
		
		private int age; 
		private String name;
		
		public constructorExample() {
			this (29, "Krishna");
		}

		
		public constructorExample(int i, String string) {
			age = i;
			name = string;
		}
		
		@Override
		public String toString() {
			return "Name: " + name + " \nAge: " + age + "\n\n";
		}


		public static void main(String[] args) {
			constructorExample p = new constructorExample();
			System.out.println(p);
		}
	}

