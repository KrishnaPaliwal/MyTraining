package com.Training.General_Program;

import java.util.*; 

public class CountDuplicates
{
  public static void main(String[] args)
  {
	String s = "Krishna Paliwal Krishna Paliwal Krishna is  person person good is";
    
		Map<String, Integer> map = countDuplicates(s);
        for(Map.Entry<String, Integer> entry : map.entrySet()) {
      	System.out.println("Key : "+entry.getKey()+" , Value : "+entry.getValue());
      }
  }
  
  public static Map<String, Integer> countDuplicates(String s) {
	    StringTokenizer st = new StringTokenizer(s, " ");
	    
	    Map<String, Integer> map = new HashMap<String, Integer>();
	    while(st.hasMoreTokens())
	    {
	      String key = st.nextToken();
	      if(!map.containsKey(key)) {
	      	map.put(key, 1);
	      }
	      
	      else {
	        map.put(key, map.get(key)+1);
	      }
	      
	    }
	    return map;
  }
}