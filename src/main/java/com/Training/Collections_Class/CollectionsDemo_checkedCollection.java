package com.myTraining.Collections_Class;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CollectionsDemo_checkedCollection {

	public void addUnrelatedInfo(List list) {
	    list.add(5);
	}
	public static void main(String[] args)
{
		CollectionsDemo_checkedCollection cDc = new CollectionsDemo_checkedCollection();	
	List<String> stringList = new ArrayList<>();
	List lst = Collections.checkedList(stringList, String.class);
	lst.add("hello");
	cDc.addUnrelatedInfo(lst);
	lst.add("world");
	System.out.println(lst);
	}
}