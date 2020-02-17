package com.Training.Memory_Management.EscapingReferences;

import java.util.List;
import java.util.Map;

public class CustomerAp {

	public static void main(String[] args) {
		
	// CustomerMapRecords
		/*
		 * CustomerMapRecords records = new CustomerMapRecords();
		 * 
		 * records.addCustomer(new Customer("John")); records.addCustomer(new
		 * Customer("Simon"));
		 * 
		 * for (Customer next : records.getCustomers().values()) {
		 * System.out.println(next); }
		 * 
		 * Map<String, Customer> mycustomer = records.getCustomers();
		 * 
		 * mycustomer.clear();
		 * 
		 * for (Customer next : records.getCustomers().values()) {
		 * System.out.println(next); }
		 * 
		 * mycustomer.put("Krishna", new Customer("Krishna"));
		 * 
		 * for (Customer next : records.getCustomers().values()) {
		 * System.out.println(next); }
		 */
	
	//CustomerListRecords
		
		CustomerListRecords listRecords = new CustomerListRecords();
		
		listRecords.addCustomer(new Customer("Subhash"));
		listRecords.addCustomer(new Customer("Praveen"));
		
		
		List<Customer> newList = listRecords.getCustomers();
		
		newList.clear();
		
		for(Customer c : newList) {
			System.out.println("Customer:"+c);
		}
	}

}
