package com.Training.Collection_Framwork.Collection_Interface.SET_Interface.TreeSet;

//It is must to implement Comparable interface if adding custom objects in TreeSet
public class Book implements Comparable<Book> {

	private String name;

	private int id;

	public Book() {

	}

	public Book(int id, String name) {

		this.id = id;
		this.name = name;
	}

	public int compareTo(Book o) {
		return this.name.compareTo(o.name);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String toString() {

		String s = "name:" + name;

		return s;

	}
}
