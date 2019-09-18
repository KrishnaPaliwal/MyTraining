package com.myTraining.Multi_Threading;

// MultiTreading by extending Thread class

public class ThreadTestByExtendingThreadclass {

	public static void main(String[] args) 
	{
		
        RunnerExtendsThread runner1 = new RunnerExtendsThread();
        runner1.start();
         
        RunnerExtendsThread runner2 = new RunnerExtendsThread();
        runner2.start();

        RunnerExtendsThread runner3 = new RunnerExtendsThread();
        runner3.start();
    }
}

class RunnerExtendsThread extends Thread {
	 
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