package com.myTraining.Inheritance;

public class VehicleUtility {

	public static void main(String args[])
	{
		Vehicle vehical = new TwoWheeler();
		
		//TwoWheeler twowheelerq = (TwoWheeler) new Vehicle(); // Wrong : java.lang.ClassCastException
		
		TwoWheeler twowheeler = (TwoWheeler)vehical; // correct : downcasting
		System.out.println(twowheeler.returnId());
		twowheeler.returnSteeringHandle("");
		
		
		TwoWheeler tw1 = new TwoWheeler(); 
		tw1.returnSteeringHandle("ddsd");
		
		System.out.println(vehical.returnId());

		// TwoWheeler twowheelerrr =  new Vehicle(); // wrong : Type mismatch: cannot convert from Vehicle to TwoWheeler
		
		// TwoWheeler tw2 = (TwoWheeler )new Vehicle(); // wrong : java.lang.ClassCastException
	}
	
}

class Vehicle {

	Integer id;
	String name;
	String licenceNumber;
	
	Integer returnId()
	{
		return 5;
	}
}

class TwoWheeler extends Vehicle
{
	String steeringHandle;
	
	public String returnSteeringHandle(String s)
	{
		return s;
	}
	
	public Integer returnId()
	{
		return 10;
	}
}

class FourWheeler extends Vehicle
{
	String steeringWheel;
	
	public String returnSteeringWheel(String s)
	{
		return s;
	}
}
