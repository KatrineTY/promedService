package com.javaschool.listeners;

import com.javaschool.services.PromedService;
import org.apache.activemq.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;

@Component
public class ActiveMQListener {
    @Autowired
    private PromedService promedService;

    @JmsListener(destination = "queue/rehaQueuePromed")
    public void receiveMessage(final Message jsonMessage) throws JMSException {
        System.out.println("Received message " + jsonMessage);
        if (jsonMessage instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) jsonMessage;
            String message = textMessage.getText();
            String[] data = message.split(",");
            String promedName = data[0].split(":")[1];
            int count = Integer.parseInt(data[1].split(":")[1]);
            promedService.subtractCount(promedName, count);
        }
    }

}
