package com.myTraining.General_Program;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class PreventDuplicateCustomObjectInSet {
	
	public static void main(String []rr)
	{
		Set<Price> setOfCustomObj = new HashSet<Price>();
		
		setOfCustomObj.add(new Price("Mango", 10, 2005, 1000.9));
		setOfCustomObj.add(new Price("Orange", 20, 2006, 2000.9));
		setOfCustomObj.add(new Price("Orange", 20, 2008, 2000.9)); // duplicate
		setOfCustomObj.add(new Price("apple", 30, 2008, 5000.9));
		
		for(Price price : setOfCustomObj)
		{
			System.out.println(price.getItem()+" "+price.getPrice()+" "+price.getExpiry()+" "+price.getQuantity());
		}
	}	


}

class Price {
	
	private String item;
	
	private Integer price;
	
	private int expiry;
	private double quantity;
	
	public Price(String item, Integer price, int expiry, double quantity)
	{
		this.item= item;
		this.price = price;
		this.expiry = expiry;
		this.quantity = quantity;
	}
	
	public String getItem()
	{
		return item;
	}
	
	public Integer getPrice()
	{
		return price;
	}
	
	public int hashCode()
	{	System.out.println("in hahCode method");
		int hashcode = 0;				
		hashcode = item.hashCode()+ price.hashCode();
		System.out.println("hashcode "+hashcode);
		return hashcode;	
	}
	
	public boolean equals(Object obj)
	{	System.out.println("in equals method");
		Price p = (Price)obj;
		if(p.item.equals(this.item) && this.price== p.price) return true;
		else return false;
			
	}

	public int getExpiry() {
		return expiry;
	}

	public void setExpiry(int expiry) {
		this.expiry = expiry;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}
	
/*	public int hashcode()
	{
		System.out.println("in hashcode method");
		int hashcode = 0;
		hashcode=price*20;
		hashcode = hashcode+item.hashCode();
		
		return hashcode;
		
	}
	
	public boolean equals(Object obj)
	{	
		System.out.println("in equals method");
		Price p = (Price)obj;
		if(p.item.equals(this.item) && this.price== p.price) return true;
		else return false;
	}*/
	
/*	   public int hashCode(){
	        System.out.println("In hashcode");
	        int hashcode = 0;
	        hashcode = price*20;
	        hashcode += item.hashCode(); 
	        System.out.println("hashcode "+hashcode);
	        return hashcode;
	        
	    }
	     
	    public boolean equals(Object obj){
	        System.out.println("In equals");
	        if (obj instanceof Prices) {
	            Price pp = (Price) obj;
	            return (pp.item.equals(this.item) && pp.price == this.price);
	        } else {
	            return false;
	        }
	    }*/
	     
}
