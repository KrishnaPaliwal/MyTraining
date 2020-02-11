package com.Training.General_Program;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static java.text.MessageFormat.format;
 
class IPAddress
{

	public static void main(String args[]) throws Exception
   {
      System.out.println(InetAddress.getLocalHost());
      
      System.out.println(InetAddress.getLoopbackAddress());

   }
   
}