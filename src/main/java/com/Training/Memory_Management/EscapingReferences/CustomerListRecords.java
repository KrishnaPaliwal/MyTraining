package com.Training.Memory_Management.EscapingReferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomerListRecords {

	List<Customer> customerList;
	
	public CustomerListRecords() {
		this.customerList = new ArrayList<Customer>();
	}
	
	public void addCustomer(Customer c) {
		customerList.add(c);
	}
	
	public List<Customer> getCustomers() {
		return Collections.unmodifiableList(this.customerList);
	}
 	
}
