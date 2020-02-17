package com.Training.Memory_Management.EscapingReferences;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CustomerMapRecords {
	private Map<String, Customer> records;
	
	public CustomerMapRecords() {
		this.records = new HashMap<String, Customer>();
	}
	
	public void addCustomer(Customer c) {
		this.records.put(c.getName(), c);
	}
		
	public Map<String, Customer> getCustomers() {
		return Collections.unmodifiableMap(this.records);
	}
}
