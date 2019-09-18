package com.myTraining.CoreJAVATrainingByAnjali;

import java.awt.*;
import java.awt.event.*;

class MyFrame2 implements TextListener
{
   Frame f;
   Label l1,l2;
   TextField tx1,tx2;
   String s;
   
   MyFrame2()
   { 
     f=new Frame("MyWindow");
     f.setSize(200,300);
     f.setLayout(new FlowLayout());
     f.setVisible(true);
    
     l1=new Label("Text............");
     l2=new Label("Dulicate Text...");
     
   
     tx1=new TextField(10);
     tx2=new TextField(10);

     f.add(l1);
     f.add(tx1);

     f.add(l2);
     f.add(tx2);
  
     //f.add(bt);

     tx1.addTextListener(this);
    }

   public void textValueChanged(TextEvent e)
   {
     s=tx1.getText();
     tx2.setText(s);
   }
    
   public static void main(String args[])
   {
       new MyFrame2();
   }
}