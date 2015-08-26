package com.appleframework.jms.core.consumer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;


/**
 * @author xusm
 * 
 */
public abstract class ObjectMessageConusmer extends MessageConusmer implements MessageListener {
	
	protected Object message;
	
	public Object getMessage() {
		return message;
	}

	public void setMessage(Object message) {
		this.message = message;
	}

	public void onMessage(Message message) {
		try {
			Object object = ((ObjectMessage) message).getObject();
			this.message = object;
		} catch (JMSException e) {
			e.printStackTrace();
		}
		processMessage();
	}
	
	public abstract void processMessage();

}
