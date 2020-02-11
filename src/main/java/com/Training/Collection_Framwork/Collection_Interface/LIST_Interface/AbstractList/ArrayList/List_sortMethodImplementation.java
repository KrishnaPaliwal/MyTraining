package com.Training.Collection_Framwork.Collection_Interface.LIST_Interface.AbstractList.ArrayList;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;

public class List_sortMethodImplementation {

	   @SuppressWarnings({"unchecked", "rawtypes"})
	    static void sort(Comparator<? super E> c) {
	        Object[] a = List.toArray();
	        Arrays.sort(a, (Comparator) c);
	        ListIterator<E> i = this.listIterator();
	        for (Object e : a) {
	            i.next();
	            i.set((E) e);
	        }
	    }
}
