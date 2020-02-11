package com.Training.Multi_Threading;

class Runner extends Thread {

    @Override
    public void run() {
        for(int i=0; i<5; i++) {
            System.out.println("Hello: " + i);
            
            try {
                Thread.sleep(1090);

            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
}


public class Application {

    
    public static void main(String[] args) {
        Runner runner1 = new Runner();
        runner1.start();
        try {
        	synchronized (runner1) {
			int i = 0;
				while(i<4){
					runner1.wait();
				}
        	}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        Runner runner2 = new Runner();
        runner2.start();

    }

}
