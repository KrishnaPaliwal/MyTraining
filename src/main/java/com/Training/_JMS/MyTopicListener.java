
package com.Training._JMS;

import javax.jms.*;  
public class MyTopicListener implements MessageListener {  
  
    public void onMessage(Message m) {  
        try{  
        TextMessage msg=(TextMessage)m;  
      
        System.out.println("following message is received:"+msg.getText());  
        }catch(JMSException e){System.out.println(e);}  
    }  
}  