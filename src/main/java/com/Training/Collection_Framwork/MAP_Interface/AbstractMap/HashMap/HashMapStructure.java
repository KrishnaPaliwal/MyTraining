package com.Training.Collection_Framwork.MAP_Interface.AbstractMap.HashMap;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HashMapStructure {
	  
    /**
     */
    public static void main(String[] args) {
          
        Country india=new Country("India",1000);
        Country japan=new Country("Japan",10000);
        Country japan1=new Country("Japan",10000);
        Country INDIA = new Country("INDIA", 1000);
          
        Country france=new Country("France",2000);
        Country russia=new Country("Russia",20000);
          
        Map<Country,String> countryCapitalMap=new HashMap<Country,String>();
        countryCapitalMap.put(india,"Delhi");
        countryCapitalMap.put(japan,"Tokyo");
        countryCapitalMap.put(japan1,"Tokyoyyyy");
        countryCapitalMap.put(INDIA,"Mumbai");
        countryCapitalMap.put(france,"Paris");
        countryCapitalMap.put(russia,"Moscow");
          
        Iterator<Country> countryCapitalIter=countryCapitalMap.keySet().iterator();//put debug point at this line
        while(countryCapitalIter.hasNext())
        {
            Country countryObj=countryCapitalIter.next();
            String capital=countryCapitalMap.get(countryObj);
            System.out.println(countryObj.getCountryName()+"----"+capital);
            }
        }
  
  
} 
