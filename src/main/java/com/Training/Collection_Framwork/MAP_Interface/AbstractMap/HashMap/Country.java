package com.myTraining.Collection_Framwork.MAP_Interface.AbstractMap.HashMap;

public class Country {

	 String countryName;
	 long population;

	 public Country(String countryName, long population) {
	  super();
	  this.countryName = countryName;
	  this.population = population;
	 }
	 public String getCountryName() {
	  return countryName;
	 }
	 public void setCountryName(String name) {
	  this.countryName = countryName;
	 }
	 public long getPopulation() {
	  return population;
	 }
	 public void setPopulation(long population) {
	  this.population = population;
	 }

	 // If length of name in country object is even then return 31(any random number) and if odd then return 95(any random number).
	 // This is not a good practice to generate hashcode as below method but I am doing so to give better and easy understanding of hashmap.
	 @Override
	 public int hashCode() {
	  if(this.countryName.length()%2==0)
	   return 31;
	  else 
	   return 95;
	 }
	 @Override
	 public boolean equals(Object obj) {

	  Country other = (Country) obj;
	   if (countryName.equalsIgnoreCase((other.countryName)))
	   return true;
	  return false;
	 }

	}