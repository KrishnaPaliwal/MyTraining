package com.myTraining.CoreJAVATrainingByAnjali;

import java.awt.*;
import java.awt.event.*;

class MyFrame3 implements ItemListener
{
  Frame f;
  Checkbox c1,c2,c3,c4,c5;
  CheckboxGroup cbg;
   
   MyFrame3()
   { 
     f=new Frame("MyWindow");
     f.setSize(200,300);
     f.setLayout(new FlowLayout());
     f.setVisible(true);
     f.setBackground(Color.red);
     
      cbg=new CheckboxGroup();
     c1=new Checkbox("Red",cbg,true);
     c2=new Checkbox("Green",cbg,false);
     c3=new Checkbox("Blue",cbg,false);

     f.add(c1);
     f.add(c2);
     f.add(c3);

     c1.addItemListener(this);
     c2.addItemListener(this);
     c3.addItemListener(this);
    }

   public void itemStateChanged(ItemEvent e)
   {
     if(c1.getState())
       f.setBackground(Color.red);
     if(c2.getState())
       f.setBackground(Color.green);
     if(c3.getState())
       f.setBackground(Color.blue);
   }
    
   public static void main(String args[])
   {
       new MyFrame3();
   }
}