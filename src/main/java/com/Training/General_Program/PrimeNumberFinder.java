package com.myTraining.General_Program;

public class PrimeNumberFinder {
	
	public void isPrimeNumber(Integer n)
	{
		for(int i=2; i<=n-1; i++)
		{
			if(n%i==0)
			{
				System.out.println("Not a prime number");
				break;
			}
			
			System.out.println("Number is Prime");
		}

	
		
	}
	
	public static void main(String [] rr)
	{	
		int n = Integer.parseInt(rr[0]);
		
		PrimeNumberFinder pnf = new PrimeNumberFinder();
		pnf.isPrimeNumber(n);
		
	}

}
