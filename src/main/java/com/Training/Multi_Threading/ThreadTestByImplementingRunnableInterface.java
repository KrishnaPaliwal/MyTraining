package com.myTraining.Multi_Threading;
/*
//MultiTreading by implementing Runnable Interface
public class ThreadTestByImplementingRunnableInterface {

    public static void main(String[] args) {
        Thread thread1 = new Thread(new RunnerImplementRunnable());
        thread1.start();
        
        Thread thread2 = new Thread(new RunnerImplementRunnable());
        thread2.start();
        
        Thread thread3 = new Thread(new RunnerImplementRunnable());
        thread3.start();
    }
 
}

class RunnerImplementRunnable implements Runnable {
	 
    @Override
    public void run() {
        for(int i=0; i<5; i++) {
            System.out.println("Hello: " + i);
             
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
         
    }
     
}
*/

public class ThreadTestByImplementingRunnableInterface{
	
	public static void main(String[] argsss){
	
	Thread th = new Thread(new classimplementsRunnable());
	th.start();
	Thread th2 = new Thread(new classimplementsRunnable());
	th2.start();
	}
}

class classimplementsRunnable implements Runnable {
	
	@Override
	public void run()
	{
		for(int i=0; i<500; i++)
		{
			System.out.println(i);
			
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
		}
	}
}