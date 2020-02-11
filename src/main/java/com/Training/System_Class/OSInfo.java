package com.Training.System_Class;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;

public class OSInfo {

    public static void main(String[] args) {
        Properties prop = System.getProperties();
       
        Set<Object> keySet = prop.keySet();
        for(Object obj : keySet){
            System.out.println("System Property: {"+obj.toString()+","+System.getProperty(obj.toString())+"}");
        }

//currentTimeMillis method
        System.out.println(System.currentTimeMillis());
        
        Date date = new Date(System.currentTimeMillis()); 
        System.out.println(date);
        
        Date date2 = new Date();
        System.out.println(date2);

        //nanoTime method
        System.out.println(System.nanoTime());
 
//getenv method
      		String pathValue = System.getenv("PATH");
            System.out.println(pathValue);

      		Map<String, String> mp = System.getenv();
      		System.out.println(mp);
      		
//////////////////////////////////////////////////////////////////////////////////////////////
// Read from the file and write on the file examples
      		try
      		{
      		//Reading file and writing line by line using Scanner class and on console
      			FileInputStream fis1 = new FileInputStream("D:\\JAVA\\File Input and OutputStream\\input1.txt");      			
      			Scanner sc1 = new Scanner(fis1);
      			while(sc1.hasNextLine())
      					{
      				System.out.println(sc1.nextLine());
      			}
      			
      		//-----------------------------------------------------------------------------------      			
   //System.in.read()
      		//Reading first character of input file using System.in.read()
      			FileInputStream fis2 = new FileInputStream("D:\\JAVA\\File Input and OutputStream\\input2.txt");
      			System.setIn(fis2);
    			char c = (char) System.in.read();
    			System.out.print(c); //prints the first character from input stream
    		
    			
    			//-----------------------------------------------------------------------------------
    //FileInputStream.read  		
    		//Reading character by character complete file using FileInputStream.read method
       			FileInputStream fis3 = new FileInputStream("D:\\JAVA\\File Input and OutputStream\\input3.txt");
    			int content;
    			
    			while ((content = fis3.read()) != -1) {
    			// convert to char and display it
    			System.out.print((char) content);
    			}			

    			
      		//-----------------------------------------------------------------------------------
    			FileInputStream fis4 = new FileInputStream("D:\\JAVA\\File Input and OutputStream\\input4.txt");
    			
    			//write on the file 
    			FileOutputStream fos = new FileOutputStream("D:\\JAVA\\File Input and OutputStream\\server.log");
    			while ((content = fis4.read()) != -1) {
    				fos.write(fis4.read());
    			}
    		//------------------------------------------------------------------------------
 
    /////////////////////////////////////////////////////////////////////////
  // setOut method
    		//Redirect output to output stream
    		
    			System.setOut(new PrintStream(fos));
    			System.out.write("Hi Pankaj\n".getBytes());
 
  // setErr method  			//set error stream
    			System.setErr(new PrintStream(fos));
    			System.err.write("Exception message\n".getBytes());
      		
      		}	

      		catch(IOException e){
      			e.printStackTrace();
      		}
      		
// exit method      		
      		System.exit(1);
      		
      		
//getSecurityManager method     
        SecurityManager secManager = System.getSecurityManager();
        if(secManager == null){
			System.out.println("SecurityManager is not configured");
		
		SecurityManager mySecManager = new SecurityManager();
		System.setSecurityManager(mySecManager);
		secManager = System.getSecurityManager();
        }
		if(secManager != null){
			System.out.println("SecurityManager is configured");
		}



		
		
		/*
        Iterator<?> it = prop.values().iterator();
        while(it.hasNext())
        {
        	System.out.println(it.next());
 
        }
        */
    }

}
