package com.myTraining.hashCodeANDequals;

import java.util.HashSet;
import java.util.Set;

public class EqualsTestWhenAddingIntoSet {
    public static void main(String[] args) {
        EmployeeClass e1 = new EmployeeClass();
        EmployeeClass e2 = new EmployeeClass();
 
        e1.setId(100);
        e2.setId(100);
 
        Set<EmployeeClass> set = new HashSet();
        set.add(e1);
        set.add(e2);
        System.out.println(set);
        //Prints false in console
        System.out.println(e1.equals(e2));
    }
}

class EmployeeClass
{
    private Integer id;
    private String firstname;
    private String lastName;
    private String department;
 
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getFirstname() {
        return firstname;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getDepartment() {
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    
    public boolean equals(Object o) {
        if(o == null)
        {
            return false;
        }
        if (o == this)
        {
            return true;
        }
        if (getClass() != o.getClass())
        {
            return false;
        }
         
        EmployeeClass e = (EmployeeClass) o;
        return (this.getId() == e.getId());
         
    }
    
    @Override
    public int hashCode()
    {	int result=1;
    
    	int prime=31;
    	
    	result = prime*result+this.getId();
    	return result;
    }
}

