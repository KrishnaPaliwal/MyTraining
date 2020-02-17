package com.Training.File_Handling;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.Training.General_Program.CountDuplicates;

public class ReadFileStoreInStringAndCountDuplicates {

	public static void main(String[] args) {
		try {
			File file = new File("C:\\Program Files\\Java\\jdk1.8.0_112\\COPYRIGHT");

			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line=null;
			StringBuilder sb = new StringBuilder();
			while((line=br.readLine()) != null) {
				sb.append(line);
				sb.append(" ");
			}
			//System.out.println(sb.toString());
			
			Map<String, Integer> map = CountDuplicates.countDuplicates(sb.toString());
			
			Iterator<Entry<String, Integer>> it = map.entrySet().iterator();
			
			while(it.hasNext()){
				Entry entry = it.next();
				System.out.println("Key :"+entry.getKey()+", Value:"+entry.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
