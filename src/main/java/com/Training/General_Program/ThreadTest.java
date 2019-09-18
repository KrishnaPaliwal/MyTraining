package com.myTraining.General_Program;

public class ThreadTest extends Thread{
	
	public void run()
	{
		try {
			System.out.println("kridn`");
			Thread.sleep(4000);
			System.out.println("kridn`");
			Thread.sleep(4000);
			System.out.println("kridn`");
			Thread.sleep(4000);
			System.out.println("kridn`");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String [] args)
	{
		ThreadTest t1 = new ThreadTest();
		t1.start();
		
		
		
	}
}
