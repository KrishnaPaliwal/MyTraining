package com.myTraining.Singleton_Class;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SixTonExample {
	public static void main(String [] args)
	{
		for(int i=0;i<24;i++)
		{
			SixTon sixTon = SixTon.getInstance();
			System.out.println(sixTon);
		}

	}

}

class SixTon {
	
	private static List<SixTon> list =  Collections.synchronizedList(new ArrayList<SixTon>());
	private static int incrmt=0;
	public static SixTon instance = null;
	private SixTon()
	{
		
	}
	public static SixTon getInstance()
	{
		if(list.size()<6)
		{
			instance =  new SixTon();
			list.add(instance);
			return instance;
			
		}
		
		else {
			SixTon st = list.get(incrmt);
			incrmt = (incrmt+1)%list.size();
			return st;
		}
		
	}
}