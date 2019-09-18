package com.myTraining.JUnit;

public class Money {
	private int fAmount; 
    private String fCurrency;
    
    public Money(int ammount , String currency)
    {
    	this.fAmount = ammount;
    	this.fCurrency = currency;
    }


    public int amount()
    {
    	return fAmount;
    }
   
    public String currency()
    {
    	return fCurrency;
    }
    
    public Money add(Money m)
    {
    	return new Money(amount()+m.amount(), currency());
    }

}

