package com.Training.General_Program;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PreventDuplicateCustomObjectInList {
 
    public static void main(String a[]){
         
        List<Prices> listOfCustomObj = new ArrayList<Prices>();
        
        listOfCustomObj.add(new Prices("Banana", 20));

        if(!listOfCustomObj.contains(new Prices("Banana", 20)))
        	{
        		listOfCustomObj.add(new Prices("Banana", 20));
        	}
        
        listOfCustomObj.add(new Prices("Apple", 40));
        listOfCustomObj.add(new Prices("Orange", 30));
        listOfCustomObj.add(new Prices("Banana", 70));

        System.out.println("After insertion:");
        for(Prices pr:listOfCustomObj){
            System.out.println(pr);
        }
        
    }
}
 
class Prices{
     
    private String item;
    private int prices;
     
    public Prices(String itm, int pr){
        this.item = itm;
        this.prices = pr;
    }
    
    //List does not use hashCode method . It only use equals method inside contains method to check 
    // duplicate object.
     
/*    public int hashCode(){
        System.out.println("In hashcode");
        int hashcode = 0;
        hashcode = prices*20;
        hashcode += item.hashCode(); 
        System.out.println("hashcode "+hashcode);
        return hashcode;
        
    }*/
     
    public boolean equals(Object obj){
        System.out.println("In equals");
        if (obj instanceof Prices) {
            Prices pp = (Prices) obj;
            return (pp.item.equals(this.item) && pp.prices == this.prices);
        } else {
            return false;
        }
    }
     
    public String getItem() {
        return item;
    }
    public void setItem(String item) {
        this.item = item;
    }
    public int getPrices() {
        return prices;
    }
    public void setPrices(int Prices) {
        this.prices = Prices;
    }
     
    public String toString(){
        return "item: "+item+"  Prices: "+prices;
    }
}