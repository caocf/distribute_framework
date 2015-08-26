package com.appleframework.jms.core.producer;

import java.io.Serializable;

import javax.jms.Destination;

import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;

import com.appleframework.jms.core.creator.JmsByteMessageCreator;
import com.appleframework.jms.core.creator.JmsObjectMessageCreator;
import com.appleframework.jms.core.creator.JmsTextMessageCreator;


/**
 * @author xusm
 * 
 */
public class JmsMessageProducer implements MessageProducer {

	private JmsTemplate jmsTemplate;
	private Destination destination;

	public void sendObject(Serializable message) throws JmsException {
		this.jmsTemplate.send(destination, new JmsObjectMessageCreator(message));
	}
	
	public void sendByte(byte[] message) throws JmsException {
		this.jmsTemplate.send(destination, new JmsByteMessageCreator(message));
	}
	
	public void sendText(String message) throws JmsException {
		this.jmsTemplate.send(destination, new JmsTextMessageCreator(message));
	}

	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public Destination getDestination() {
		return destination;
	}

	public void setDestination(Destination destination) {
		this.destination = destination;
	}

}
