package com.Training.General_Program;

import javax.jms.*;

interface Marker{   
}

class MyException extends Exception {   
    public MyException(String s){
        super(s);
    }
}

class AA  {
    void m1() throws MyException{        
         if((this instanceof Marker)){
             System.out.println("successfull");
         }
         else {
             throw new MyException("Must implement interface Marker ");
         }      
    }   
}

public class CustomMarkerInterfaceExample  extends AA implements Marker
{ // if this class will not implement Marker, throw exception
    public static void main(String[] args)  {
        CustomMarkerInterfaceExample a= new CustomMarkerInterfaceExample();
        try {
            a.m1();
        } catch (MyException e) {

            System.out.println(e);
        }


    }

}