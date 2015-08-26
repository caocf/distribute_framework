package com.appleframework.jms.core.creator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.MessageCreator;

public class JmsTextMessageCreator implements MessageCreator {

	private String string;

	public JmsTextMessageCreator(String string) {
		this.string = string;
	}
	
	public Message createMessage(Session session) throws JMSException {
		Message message = session.createTextMessage(this.string);
		return message;
	}
}
