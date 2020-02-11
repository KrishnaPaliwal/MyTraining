package com.Training.Wrapper_Classes;

public class myWrapperClass {
	public static void main(String... args)
	{	//------- ---------------------------
		int x=56;
		Integer y = new Integer(78);
		int z = x + y.compareTo(x);
		System.out.println("z\t"+z);
		
		System.out.println("Min Value\t"+Integer.MIN_VALUE+"\n"+"Max Value\t"+
								Integer.MAX_VALUE+"\nSize\t"+Integer.SIZE);
		
		
		
		try
		{	int A = Integer.parseInt("12u34");
			System.out.println("A\t"+A);
		}
		
		catch(Exception e)
		{
			System.out.println("Oops, we went to far, better go back to 0!");
		}
		//------------------------------
		char c ='C';
		Character C = new Character('C');
		Character s = Character.MIN_VALUE;
		System.out.println(s);
		
	
	}

}
