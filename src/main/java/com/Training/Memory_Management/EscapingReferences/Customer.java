package com.Training.Memory_Management.EscapingReferences;

public class Customer {
	private String name;

	public String getName() {
		return name;
	}

	public Customer(String name) {
		this.name = name;
	}
	
		public String toString() {
		return name;
	}
	
}
