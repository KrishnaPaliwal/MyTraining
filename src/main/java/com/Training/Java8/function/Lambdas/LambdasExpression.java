package com.Training.Java8.function.Lambdas;

import java.util.function.*;

public class LambdasExpression {

    public static void main(String[] args) {

    	Predicate<String> stringLen  = (s)-> s.length() > 10;
        System.out.println(stringLen.test("Addresses") + " - Addresses are more than 10");

        
        Function<Integer,String> converter = (num)-> Integer.toString(num);
        System.out.println("length " + converter.apply(45).length());

        Consumer<String> consumerStr = (s) -> System.out.println(s.toLowerCase());
        consumerStr.accept("Krishna");

        Supplier<String> s  = ()-> "Test Program";
        System.out.println(s.get());
        
        BinaryOperator<Integer> add = (a, b) -> a - b;
        System.out.println("Minus " + add.apply(100, 25));
        
        UnaryOperator<String> str  = (msg)-> msg.toUpperCase();
        System.out.println(str.apply("make it upper case"));
    }
    
}
