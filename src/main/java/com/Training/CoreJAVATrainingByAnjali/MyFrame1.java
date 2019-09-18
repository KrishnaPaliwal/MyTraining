package com.myTraining.CoreJAVATrainingByAnjali;

import java.awt.*;
import java.awt.event.*;

class MyFrame1 implements ActionListener
{
   Frame f;
   Label l1,l2,l3;
   TextField tx1,tx2,tx3;
   Button bt;
   String s1,s2;
   int a,b,c;
   
   MyFrame1()
   { 
     f=new Frame("MyWindow");
     f.setSize(200,300);
     f.setLayout(new FlowLayout());
     f.setVisible(true);
    
     l1=new Label("Number One");
     l2=new Label("Number Two");
     l3=new Label("Total");
   
     tx1=new TextField(10);
     tx2=new TextField(10);
     tx3=new TextField(10);

     bt=new Button("ADD");

     f.add(l1);
     f.add(tx1);

     f.add(l2);
     f.add(tx2);

     f.add(l3);
     f.add(tx3);
  
     f.add(bt);

     //bt.addActionListener(this);
    }

   public void actionPerformed(ActionEvent e)
   {
     c=0;
     s1=tx1.getText();
     s2=tx2.getText();
     
     a=Integer.parseInt(s1);
     b=Integer.parseInt(s2);
   
     c=a+b;
   
     tx3.setText(" "+c+"");
   }
    
   public static void main(String args[])
   {
       new MyFrame1();
   }
}