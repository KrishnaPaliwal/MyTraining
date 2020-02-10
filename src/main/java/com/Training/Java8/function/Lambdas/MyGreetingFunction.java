package com.Training.Java8.function.Lambdas;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Consumer;

//custom functional interface
@FunctionalInterface
interface GreetingFunction {
void sayMessage(String message);
}

public class MyGreetingFunction {

    public static void main(String[] args) {

        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("run 1");
            }
        };

        Runnable r2 = () -> System.out.println("run 2");

        r1.run();
        r2.run();
        
        BiFunction<String, String, String> concat = (a, b) -> a + b;
        String sentence = concat.apply("Today is ", "a great day");
        System.out.println(sentence);
        
        Consumer<String> hello = name -> System.out.println("Welcome "+name);
        for (String name : Arrays.asList("Krishna", "Prerita", "Meena")) {
            hello.accept(name);
        }
        
         GreetingFunction greeting = message -> System.out.println("Greating message " + message);
         greeting.sayMessage("Hello");
    }
}

