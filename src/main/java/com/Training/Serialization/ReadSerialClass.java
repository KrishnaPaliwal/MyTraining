package com.Training.Serialization;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Calendar;

public class ReadSerialClass {
    public static void main(String [] args) {
        String filename = "D://time22.txt";
		
        SerialClass serializedClassObject = null;

        try{
        	FileInputStream fis = new FileInputStream(filename);
        	ObjectInputStream in = new ObjectInputStream(fis);
        	serializedClassObject = (SerialClass)in.readObject();
            in.close();
            
            System.out.println("hashCode of serializedClassObject : "+serializedClassObject.hashCode());
            System.out.println("serializedClassObject : "+serializedClassObject);
            
        }catch(IOException ex){
            ex.printStackTrace();
        }catch(ClassNotFoundException cnfe){
            cnfe.printStackTrace();
        }

        // print out restored time
        System.out.println("Restored Current time: " + serializedClassObject.getCurrentTime());
        System.out.println("Restored staticTime time: " + serializedClassObject.staticTime);
        System.out.println("Restored transientTime time: " + serializedClassObject.transientTime);
        

        // print out the current time
        System.out.println("Current time: " 
			+ Calendar.getInstance().getTime());

     }

}
