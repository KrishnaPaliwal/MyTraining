package com.Training.Singleton_Class;

public class SingletonExample {
	 
    // Static member holds only one instance of the
    // SingletonExample class
    private static SingletonExample singletonInstance;
 
    // SingletonExample prevents any other class from instantiating
    private SingletonExample() {
    }
 
    // Providing Global point of access
    public static SingletonExample getSingletonInstance() {
        if (null == singletonInstance) {
            singletonInstance = new SingletonExample();
        }
        return singletonInstance;
    }
 
    public void printSingleton(){
        System.out.println("Inside print Singleton");
    }
}
