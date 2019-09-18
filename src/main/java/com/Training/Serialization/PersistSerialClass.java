package com.myTraining.Serialization;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

//Class to persist the time in a flat file time.ser

public class PersistSerialClass {
    public static void main(String [] args) {
       
    	//String filename = "D://time2.txt";

        SerialClass serializedClassObject = new SerialClass();

        System.out.println("hashCode of serializedClassObject before persist : "+serializedClassObject.hashCode());
        System.out.println("serializedClassObject before persist: "+serializedClassObject);
        
        try{
        	
        	FileOutputStream fos = new FileOutputStream("D://time22.txt");
        	BufferedOutputStream out = new BufferedOutputStream(fos);
        	//ObjectOutputStream out = new ObjectOutputStream(fos);
        	
        	System.out.println("transientTime time before persist: " + serializedClassObject.transientTime);
            out.write(serializedClassObject);
            //out.writeObject(serializedClassObject);
            
            out.close();
            
        }catch(IOException ex){
            ex.printStackTrace();
        }
     }

}
