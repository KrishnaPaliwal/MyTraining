package com.Training.Multi_Threading_Concurrency.ThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolExample {
	
	public static void main( String [] args)
	{
		ExecutorService service = Executors.newSingleThreadExecutor();
		
		for(int i=0; i<100; i++)
		{
			service.submit(new MyTask(i));
			
		}
			
	}

}

class MyTask implements Runnable {
	
	private int taskId;
	
	public MyTask()
	{
		
	}
	
	public MyTask(int task)
	{
		this.taskId = task;
	}

	@Override
	public void run() {
		System.out.println("Task ID "+this.taskId + " is performed by " +Thread.currentThread().getName());
		
	}
	
	
}