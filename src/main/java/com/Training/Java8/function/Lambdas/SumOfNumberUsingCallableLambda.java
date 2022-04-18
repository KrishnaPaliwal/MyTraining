package com.learn.lambda;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class SumOfNumberUsingCallableLambda {
	
	public static int[] array = IntStream.rangeClosed(0, 500).toArray();
	public static int total = IntStream.rangeClosed(0, 500).sum();
	

	public static void main(String args[]) throws InterruptedException, ExecutionException {
		
		Callable callable1 = () -> {
			int sum =0;
			for(int i=0; i<array.length/2; i++) {
				sum = sum+array[i];
			}
			System.out.println();
			
			return sum;
		};
		
		Callable callable2 = () -> {
			
			int sum=0;
			for(int i=array.length/2; i<array.length; i++) {
				sum = sum+array[i];
			}
			return sum;
		};
	
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		List<Callable<Integer>> list = Arrays.asList(callable1, callable2);
		
		List<Future<Integer>> futurelist = executorService.invokeAll(list);
		
		int sum = 0;
		int k=0;
		for(Future<Integer> future : futurelist) {
			sum =	sum+future.get();
			System.out.println("Sum of "+ ++k + "is "+future.get());
		}
		
		System.out.println("Correct sum is :"+ total);
		System.out.println("Sum from callable is:"+sum);
		
		executorService.shutdown();

	}
	
	
	
}
