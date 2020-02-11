package com.Training.ExceptionHandling_try_catch_finally_Throw_Throws;

public class TryWithResources
{
	 
    public static void main(String[] args) {
         
        try(Temp temp = new Temp()) {
             
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }     
    }   
}
 

class Temp implements AutoCloseable {
	 
    @Override
    public void close() throws Exception {
        System.out.println("Closing!");
        throw new Exception("oh no!");
    }
     
}
