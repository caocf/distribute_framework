package com.appleframework.jms.core.creator;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.MessageCreator;

public class JmsObjectMessageCreator implements MessageCreator {
	
	private Serializable serializable;

	public JmsObjectMessageCreator(Serializable serializable) {
		this.serializable = serializable;
	}
	
	public Message createMessage(Session session) throws JMSException {
		Message message = session.createObjectMessage(this.serializable);
		return message;
	}
}
