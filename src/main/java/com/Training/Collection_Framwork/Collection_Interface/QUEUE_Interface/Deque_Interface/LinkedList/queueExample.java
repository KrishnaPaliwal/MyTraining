package com.Training.Collection_Framwork.Collection_Interface.QUEUE_Interface.Deque_Interface.LinkedList;

import java.util.LinkedList;
import java.util.Queue;


public class queueExample {

    /**
     * Example method for using a Queue
     */
    public queueExample() {
    
        Queue<String> queue = new LinkedList();
        
        //Using the add method to add items.
        //Should anything go wrong an exception will be thrown.

//add method
        queue.add("item1");
        queue.add("item2");
        queue.
        
 //offer method

        //Using the offer method to add items.
        //Should anything go wrong it will just return false
        queue.offer("Item3");
        queue.offer("Item4");
 
// remove method
        //Removing the first item from the queue.
        //If the queue is empty a java.util.NoSuchElementException will be thrown.
        System.out.println("remove: " + queue.remove());
 
 //element method
        //Checking what item is first in line without removing it
        //If the queue is empty a java.util.NoSuchElementException will be thrown.
        System.out.println("element: " + queue.element());
        
 // poll method
        //Removing the first item from the queue.
        //If the queue is empty the method just returns false.
        System.out.println("poll: " + queue.poll());
 
  //peek method
        //Checking what item is first in line without removing it
        //If the queue is empty a null value will be returned.
        System.out.println("peek: " + queue.peek());
        
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    	queueExample QEXP = new queueExample();
    }
}
