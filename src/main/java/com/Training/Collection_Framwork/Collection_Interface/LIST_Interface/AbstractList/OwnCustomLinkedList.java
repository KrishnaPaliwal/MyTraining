package com.myTraining.Collection_Framwork.Collection_Interface.LIST_Interface.AbstractList;

import java.util.Arrays;

public class OwnCustomLinkedList<T> {

	@Override
	public String toString() {
		return "OwnCustomLinkedList [head=" + head + "]";
	}


	private Node head;
	
	public void add(Object value)
	{
		if(head==null) head = new Node(value);
		
		Node current = head;
		Node temp = new Node(value);
		
		if(current!=null)
		{
			while(current.next!=null)
			{
				current = current.next;
			}
			
			current.next = temp;
		}
	}
	
	public Object get(int index)
	{	Node current = head.next;
		for(int i=0; i<index; i++)
		{
			current = current.next;
		}
		
		return current.data;
	}
	
	
	public static void main(String ... sss)
	{
		OwnCustomLinkedList<String> list = new OwnCustomLinkedList<>();
		list.add("Krishna");
		list.add("Paliwal");
		list.add("mds");
		list.add("ssds");
		list.add("lll");
		list.add("rrr");
		list.add("www");
		System.out.println(list.toString());
		System.out.println(list.get(2));
		//System.out.println(list.get(0));
	}
}

class Node {
	
	Node next;
	Object data;
	
	public Node(Object data)
	{
		this.data = data;
	}
}