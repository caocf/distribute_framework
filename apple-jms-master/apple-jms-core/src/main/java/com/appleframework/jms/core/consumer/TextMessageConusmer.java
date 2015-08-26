package com.appleframework.jms.core.consumer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;


/**
 * @author xusm
 * 
 */
public abstract class TextMessageConusmer extends MessageConusmer implements MessageListener {
	
	protected String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void onMessage(Message message) {
		try {
			String object = ((TextMessage) message).getText();
			this.message = object;
		} catch (JMSException e) {
			e.printStackTrace();
		}
		processMessage();
	}
	
	public abstract void processMessage();

}
