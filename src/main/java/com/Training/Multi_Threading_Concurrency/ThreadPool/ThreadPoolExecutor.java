package com.myTraining.Multi_Threading_Concurrency.ThreadPool;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolExecutor {

	public static void main(String [] args)
	{
		ExecutorService service = Executors.newFixedThreadPool(10);
		for(int i=0; i<50; i++)
		{	

			service.submit(new MyRunnableTask(i));
			
		}
		
	}
}

class MyRunnableTask implements Runnable {
	
	private int taskId;
	
	public MyRunnableTask(int task)
	{
		this.taskId = task;
	}
	
	@Override 
	public void run(){
		System.out.println("Task ID  " +this.taskId+ " is performed by "+ Thread.currentThread().getName());
		
	}
}