package com.Training.PassingValues.NonPremitivetypes;

public class PassNonPremitives {

	public static void main(String[] args) {
		Vehicle v = new Vehicle();
		v.setName("Car");
		v.setWheel(4);

		System.out.println(v);

		Vehicle v2 = changeVehicleType(v);

		System.out.println(v);
		System.out.println(v2);
	}

	public static Vehicle changeVehicleType(Vehicle v) {

		v.setName("Bike");
		v.setWheel(2);
		return v;
	}
}

class Vehicle {

	private int wheel;
	private String name;

	public void setWheel(int wheel) {
		this.wheel = wheel;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWheel() {
		return wheel;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {

		String s = "Name:" + name + " , wheel:" + wheel;

		return s;
	}
}