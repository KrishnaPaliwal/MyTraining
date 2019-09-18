package com.myTraining.Multi_Threading_Concurrency.BlockingQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueExample {

    public static void main(String[] args) throws Exception {

        BlockingQueue queue = new ArrayBlockingQueue(1024);

        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);

        Thread T1 =  new Thread(producer);
        T1.start();
        
        Thread T2 = new Thread(consumer);
        T2.start();
        
        Thread.sleep(4000);
    }

}
