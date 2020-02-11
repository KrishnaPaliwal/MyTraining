package com.Training.String_Class;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class MyStringClass {

    /**
     * This program shows how to split a string in java
     * @param args
     */
    public static void main(String[] args) {
        String line = "developer I am a java developer";
        System.out.println(line);
        int i = line.hashCode();
        System.out.println(i); // 1423387656
        String[] words = line.split(" ");
        String[] twoWords = line.split(" ", 3);
        System.out.println("String split with delimiter: "+Arrays.toString(words));
        System.out.println("String split into two: "+Arrays.toString(twoWords));
        //split string delimited with special characters
        String wordsWithNumbers = "I|am|a|java|developer";
        String[] numbers = wordsWithNumbers.split("\\|");
        System.out.println("String split with special character: "+Arrays.toString(numbers));
        
        Pattern p = Pattern.compile(" ");
        Matcher m = p.matcher(line);
        System.out.println(m.matches());
        
        System.out.println(p.matches("\\|", wordsWithNumbers));
        
        System.out.println("krifd  "+line.matches("developer.*"));
        
        String str = "Java is best programming language";
        if(str.matches("Java is.*")){
            System.out.println("String Starts with J");
       }

        String name = "Scala"; //1st String object
        String name_1 = "Scala"; //same object referenced by name variable
        String name_2 = new String("Scala"); //different String object
        String name_3 = new String("Scala"); //different String object
        String name_4 = new String("Scalac s"); //different String object
        String name_5 = new String("Scalacv"); //different String object
        String name_6 = new String("Scala"); //different String object
        //this will return true
        name_1.hashCode();
        name_2.hashCode();
        if(name==name_1){
        System.out.println("both name and name_1 is pointing to same string object");
        }
        //this will return false
        if(name==name_2){
        System.out.println("both name and name_2 is pointing to same string object");
        }

        //this will return false
        if(name_2==name_3){
        System.out.println("both name and name_2 is pointing to same string object");
        }
        int A, B;
        A=10;
        B=14;
        
        
        if(A==B)
        {
        	System.out.println("My name is Pushkar");
        	
        }
        
        else {
        	System.out.println("I an NOT Pushkar");
        }
        	
        
    }

}
