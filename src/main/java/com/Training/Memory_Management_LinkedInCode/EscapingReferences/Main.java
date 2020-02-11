package com.Training.Memory_Management_LinkedInCode.EscapingReferences;

import java.util.Map;

public class Main {

	public static void main(String[] args) {
		CustomerRecords records = new CustomerRecords();

		records.addCustomer(new Customer("John"));
		records.addCustomer(new Customer("Simon"));

		for (Customer next : records.getCustomers().values()) {
			System.out.println(next);
		}

		Map<String, Customer> mycustomer = records.getCustomers();
		
		mycustomer.clear();
		
		for (Customer next : records.getCustomers().values()) {
			System.out.println(next);
		}
		
		mycustomer.put("Krishna", new Customer("Krishna"));
		
		for (Customer next : records.getCustomers().values()) {
			System.out.println(next);
		}
	}

}
