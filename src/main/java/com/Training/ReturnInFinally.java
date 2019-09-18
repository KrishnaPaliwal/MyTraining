package com;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReturnInFinally {
	
	public int X()
	{
		int i;
		List list = new ArrayList<>();
		
		ArrayList a = (ArrayList)list;
		
		i=0;
		
		try{
			throw new IOException();
		}
		
		catch(Exception e)
		{	
			i=1;
			e.printStackTrace();
			return i;
		}
		
		
		finally
		{
			i=2;
			return i;
		}
		
	}

	public static void main(String ee[])
	{
		ReturnInFinally  f = new ReturnInFinally();
		int i = f.X();
		System.out.println(i);
	}
}
