package com.myTraining.General_Program;

public class MyOwnListImlementation<E>
    implements Cloneable, java.io.Serializable
{
    int size = 0;

    Node<E> first;

    Node<E> last;

    public MyOwnListImlementation() {
    }
	
    public boolean add(E e) {
        linkLast(e);
        return true;
    }
    
 /*   private void linkFirst(E e) {
        final Node<E> f = first;
        final Node<E> newNode = new Node<>(null, e, f);
        first = newNode;
        if (f == null)
            last = newNode;
        else
            f.prev = newNode;
        size++;

    }*/

    void linkLast(E e) {
        final Node<E> l = last;
        final Node<E> newNode = new Node<>(l, e, null);
        last = newNode;
        if (l == null)
            first = newNode;
        else
            l.next = newNode;
        size++;
    }
    
    public E get(int index) {
        return node(index).item;
    }
    
    Node<E> node(int index) {
        // assert isElementIndex(index);

        if (index < (size >> 1)) {
            Node<E> x = first;
            for (int i = 0; i < index; i++)
                x = x.next;
            return x;
        } else {
            Node<E> x = last;
            for (int i = size - 1; i > index; i--)
                x = x.prev;
            return x;
        }
    }
    
    static class Node<E> {
    	E item;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
    
    public static void main(String []d)
    {
    	MyOwnListImlementation<String> list = new MyOwnListImlementation<String>();
    	
    	
    	list.add("Krishna");
    	list.add("Paliwal");
    	list.add("Kridshna");
    	list.add("Palhiwal");
    	
    	System.out.println(list.get(0));
    	System.out.println(list.get(1));
    	System.out.println(list.get(2));
    	System.out.println(list.get(3));
    	
    }
}

