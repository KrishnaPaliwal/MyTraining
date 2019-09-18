package com.myTraining.Collection_Framwork.MAP_Interface.AbstractMap.HashMap.LinkedHashMap;

public class Country2 {

	private String countryName;
	private String population;
	
	public  Country2(String cN, String cC)
	{
		this.countryName = cN;
		this.population = cC;
	}

	public String getCountryName() {
		return countryName;
	}

	public String getCapitalCity() {
		return population;
	}
	
	public int hashCode()
	
	{
		if((countryName.length()%2)==0)
		{
			return 30;
		}
		else return 40;
	}
	
	public boolean equals(Object obj)
	{	
		Country2 ct = (Country2)obj;
		if((this.countryName).equals((ct.countryName)) && (this.population).equals(ct.population))
				{
				return true;
				}
		else return false;
		
	}
}


