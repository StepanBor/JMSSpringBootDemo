package com.gmail.stepan1983.jmsspringbootdemo;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

@Component
public class MessageReseiverDemo {

    @JmsListener(destination = "demoTopic", containerFactory = "myFactory")
    public void receiveMessage(Message message) {

//        if (message instanceof TextMessage){
            TextMessage textMessage=(TextMessage) message;

            String text= null;
            try {
                text = textMessage.getText();
            } catch (JMSException e) {
                e.printStackTrace();
            }

            System.out.println(text);
//        }
    }

}
