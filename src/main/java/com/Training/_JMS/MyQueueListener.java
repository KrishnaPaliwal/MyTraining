package com.myTraining._JMS;

import javax.jms.*;  
public class MyQueueListener implements MessageListener {  
  
    public void onMessage(Message m) {  
        try{  
        TextMessage msg=(TextMessage)m;  
      
        System.out.println("following message is received:"+msg.getText());  
        }catch(JMSException e){System.out.println(e);}  
    }  
}  
