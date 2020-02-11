package com.Training.Upcasting_and_Downcasting;

class Machine
{
	public void start()
	{
		System.out.println("Machine started");
	}
}

class Camera extends Machine
{
	public void start()
	{
		System.out.println("Camera started");
	}

	public void snap()
	{
		System.out.println("Snap taken");
	}
	
}
public class Upcasting_Downcasting {

	public static void main(String... args)
	{
		Machine M1 = new Machine();
		Camera C1 = new Camera();
		M1.start();
		C1.start();
		C1.snap();
		
		//UP casting
		Machine M2 = C1;
		M2.start();
		
		//DOWN casting
		((Camera) M2).snap();
		//or
		Camera C2 = (Camera)M2;
		C2.snap();
		C2.start();
		
		// Doesn't work -- runtime Error : cannot be cast to Camera
		Machine M3 = new Machine();
		Camera C3 = (Camera)M3;
		//C3 = (Camera)M3;
		
	}
}
