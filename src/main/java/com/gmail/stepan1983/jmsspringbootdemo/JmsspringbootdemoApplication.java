package com.gmail.stepan1983.jmsspringbootdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import javax.jms.*;
import java.util.Scanner;

@SpringBootApplication
@EnableJms
public class JmsspringbootdemoApplication {


    @Bean
    public JmsListenerContainerFactory<?> myFactory(ConnectionFactory connectionFactory,
                                                    DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        // This provides all boot's default to this factory, including the message converter
        configurer.configure(factory, connectionFactory);
        // You could still override some of Boot's default if necessary.
//        factory.setPubSubDomain(true);
        return factory;
    }

//    @Bean // Serialize message content to json using TextMessage
//    public MessageConverter jacksonJmsMessageConverter() {
//        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
//        converter.setTargetType(MessageType.TEXT);
//        converter.setTypeIdPropertyName("_type");
//        return converter;
//    }


    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String userName;

        ConfigurableApplicationContext context =
                SpringApplication.run(JmsspringbootdemoApplication.class, args);

        JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);

        if (args.length != 1) {
            System.out.println("Enter user name");
            userName = sc.nextLine();
        } else {
            userName = args[0];
        }

        while (true) {
            String messageContent = sc.nextLine();
            if (messageContent.equalsIgnoreCase("exit")) {
                break;
            }
            System.out.println("sending message from " + userName);

            jmsTemplate.send((Session session) -> (session.createTextMessage(userName + ":" + messageContent)));

        }

        System.exit(0);

    }


}



